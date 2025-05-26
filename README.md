# PDMA Madadgar Android App

## Table of Contents

* [Introduction](#introduction)
* [Features](#features)
* [Tech Stack](#tech-stack)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Configuration](#configuration)
* [Running the App](#running-the-app)
* [Project Structure](#project-structure)
* [Contributing](#contributing)
* [License & Citation](#license--citation)

## Introduction

`AndroidCP` is the Android client for the **PDMA Madadgar App**, designed to empower citizens, volunteers, and officials in Khyber Pakhtunkhwa, Pakistan with real-time disaster preparedness, response, and risk reduction tools. Developed under the Mobilise Project at the Centre for Disaster Preparedness and Management, University of Peshawar, in collaboration with PDMA, KP.

This mobile application interacts with the PDMA Madadgar backend to:

* Allow users to submit and track incident reports
* Receive early warning alerts and weather updates
* View PDMA Daily Situation Reports
* Register and manage volunteer activities
* Synchronize damage & needs assessment data
* Get push notifications for critical updates

## Features

* **User Authentication**: Secure login and registration
* **Incident Reporting**: Capture location, photos, and descriptions
* **Alert Center**: Display real-time alerts and weather warnings
* **Daily Reports**: Browse PDMA’s official situation summaries
* **Volunteer Dashboard**: Sign up and view volunteer assignments
* **Assessment Sync**: Upload and review damage/needs assessment forms
* **Push Notifications**: Receive FCM-based alerts and reminders
* **Offline Support**: Cache critical data for low-connectivity scenarios

## Tech Stack

* **Language**: Kotlin (or Java)
* **Android SDK**: Minimum SDK 21 (Android 5.0 Lollipop)
* **Architecture**: MVVM (ViewModel + LiveData)
* **Networking**: Retrofit + OkHttp
* **Local Persistence**: Room Database
* **Dependency Injection**: Dagger Hilt
* **Image Loading**: Glide
* **Push Notifications**: Firebase Cloud Messaging (FCM)
* **JSON Parsing**: Moshi or Gson

## Prerequisites

* Android Studio Arctic Fox or later
* JDK 11 or later
* Android SDK Platform Tools and API 30+
* A Google Firebase project with FCM enabled

## Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/irfansyedpg/AndroidCP.git
   cd AndroidCP
   ```
2. **Open in Android Studio**

   * Launch Android Studio, select **Open**, and navigate to the cloned folder.
3. **Sync Gradle**

   * Allow Android Studio to download and configure all dependencies.

## Configuration

1. **API Base URL**

   * Edit `build.gradle` or `Constants.kt` to set `BASE_URL` pointing to your backend (e.g., `https://api.madadgar.example.com/`).
2. **Firebase Setup**

   * Copy your `google-services.json` into `app/`.
   * Ensure the `firebase-messaging` dependency is enabled in `app/build.gradle`.
3. **Permissions**

   * Verify required permissions in `AndroidManifest.xml`: Internet, Location, Camera, Write Storage.

## Running the App

* **On Emulator**:

  * Create/emulator running API ≥ 21.
  * Click **Run** in Android Studio.

* **On Physical Device**:

  * Enable USB debugging.
  * Connect device and select it in Android Studio before running.

## Project Structure

```
AndroidCP/
├── app/
│   ├── src/main/
│   │   ├── java/...        # ViewModels, Activities, Adapters
│   │   ├── data/           # Retrofit services, Room entities, DAOs
│   │   ├── di/             # Hilt modules
│   │   ├── ui/             # Fragments and UI components
│   │   └── AndroidManifest.xml
│   ├── res/                # Layouts, drawables, strings
│   └── build.gradle
├── build.gradle
└── settings.gradle
```

## Contributing

1. Fork the repo.
2. Create a feature branch (`git checkout -b feature/YourFeature`).
3. Commit (`git commit -m "Add YourFeature"`).
4. Push (`git push origin feature/YourFeature`).
5. Open a Pull Request.

Please write clean, well-documented code and add/update unit and UI tests where applicable.

## License & Citation

Released under the MIT License.
If you adapt this client, please cite:

> “Android Madadgar app for the PDMA Madadgar App developed by the Mobilise Project at the Centre for Disaster Preparedness and Management, University of Peshawar, and Provincial Disaster Management Authority, Khyber Pakhtunkhwa.”
