# 📱 Fetch Android Challenge

- Welcome to my Android Challenge Submission!
- This Android application retrieves data from a remote API, processes it, and displays it in a user-friendly list format.

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox (2020.3.1) or newer
- An Android device or emulator running Android 14.0 (API level 34) or higher

### 🛠️ Setup

1. Clone the repository:
   ```
   git clone https://github.com/AgnayS/fetch-android-challenge.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Build the project.

## 🏃‍♂️ Running the App

1. Select an Android device or emulator.
2. Click the "Run" button in Android Studio or use the shortcut `Shift + F10`.

## 🧩 How It Works

This app demonstrates modern Android development practices:

- 📊 **Data Retrieval**: Uses OkHttp to fetch JSON data from a remote API.
- 🎨 **UI**: Implements a RecyclerView with a custom adapter for efficient list display.
- 🗃️ **Data Processing**:
    - Filters out items with blank or null names.
    - Groups items by `listId`.
    - Sorts items within groups by `name`.
    - Sorts groups by `listId`.
- 🎨 **Material Design**: Utilizes Material Design components and theming for a polished look.

### 📚 Libraries Used

- OkHttp: For network requests
- Gson: For JSON parsing
- RecyclerView: For efficient list display
- Material Components: For Material Design implementation

## 🌟 Features

- Fetches and displays data from a remote API
- Groups and sorts data for easy readability
- Implements Material Design for a modern UI
- Efficient list rendering with RecyclerView

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 🙏 Acknowledgments

- Fetch Rewards for providing the challenge
- The Android development community for invaluable resources and libraries

---
