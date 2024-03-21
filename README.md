This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

- [ ]  **Define Project Scope:** Start by clearly defining the core features and user-flows of your application. What problems will your app solve, and what will the primary use cases be?
- [ ]  **Technology Stack:**
  - **UI Framework:** Compose Multiplatform
  - **Navigation:** Compose Multiplatform
  - **Architecture:** MVVM (Model-View-ViewModel)
  - **Dependency Injection:** Kotlin (likely using a framework like Koin or Hilt)
  - **Language:** Kotlin
- [ ]  **Project Structure:** Create a modular project structure for your shared code, Android, and iOS targets.
- [ ]  **Basic UI:** Design and implement basic structural layouts for your screens in Compose Multiplatform.

## Architecture
Simple architecture with MVVM pattern.