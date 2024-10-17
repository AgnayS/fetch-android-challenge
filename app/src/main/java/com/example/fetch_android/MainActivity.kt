package com.example.fetch_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            title = "Fetch"
            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }

        recyclerView = findViewById(R.id.itemsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchItems()
    }

    private fun fetchItems() {
        val request = Request.Builder()
            .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val jsonString = response.body!!.string()
                    val groupedItems = parseAndGroupItems(jsonString)

                    runOnUiThread {
                        val adapter = GroupedItemAdapter(groupedItems)
                        recyclerView.adapter = adapter
                    }
                }
            }
        })
    }

    private fun parseAndGroupItems(jsonString: String): List<Pair<Int, List<Item>>> {
        val itemType = object : TypeToken<List<Item>>() {}.type
        val allItems: List<Item> = Gson().fromJson(jsonString, itemType)

        return allItems
            .filter { !it.name.isNullOrBlank() }
            .groupBy { it.listId }
            .map { (listId, items) ->
                listId to items.sortedBy { it.name }
            }
            .sortedBy { it.first }
    }
}

class GroupedItemAdapter(private val groupedItems: List<Pair<Int, List<Item>>>) :
    RecyclerView.Adapter<GroupedItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listIdTextView: TextView = view.findViewById(R.id.itemListId)
        val itemsContainer: LinearLayout = view.findViewById(R.id.itemsContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (listId, items) = groupedItems[position]
        holder.listIdTextView.text = "List ID: $listId"

        holder.itemsContainer.removeAllViews()

        items.forEach { item ->
            val bubbleView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.item_bubble, holder.itemsContainer, false)
            val bubbleText = bubbleView.findViewById<TextView>(R.id.bubbleText)
            bubbleText.text = "Name: ${item.name}"

            holder.itemsContainer.addView(bubbleView)
        }
    }

    override fun getItemCount() = groupedItems.size
}

data class Item(
    val id: Int,
    val listId: Int,
    val name: String?
)