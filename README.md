# Store

![Min API](https://img.shields.io/badge/API-24%2B-orange.svg?style=flat)

This app is partially built using Jetpack Compose on one side and XML framework on the other.
All files were written in Kotlin as it made work faster.
Data is stored in a SQL Lite database implemented through the Room library.
The app also uses many other Jetpack libraries.

## Tech Stack
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for building native UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html#asynchronous-flow) - Official Kotlin's tooling for performing asynchronous work.
- [MVVM/MVI Architecture](https://developer.android.com/jetpack/guide) - Official recommended architecture for building robust, production-quality apps.
- [Android Jetpack](https://developer.android.com/jetpack) - Jetpack is a suite of libraries to help developers build state-of-the-art applications.
- 
    - [Navigation](https://developer.android.com/guide/navigation) - Navigation
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel is designed to store and manage UI-related data in a lifecycle conscious way.
    - [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow) - StateFlow is a state-holder observable flow that emits the current and new state updates to its collectors.
    - [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room library provides an abstraction layer over SQLite to allow for more robust database access.
    - [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android.

## Architecture

![architecture](/media/arch.png)

## Questions

If you have any questions regarding the codebase, hit me up - vadymdrahanov@gmail.com.
