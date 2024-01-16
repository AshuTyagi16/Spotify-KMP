<h1 align="center">Spotify-KMP</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>


## Tech stack & Open-source libraries
- **Minimum SDK level**
  - Android 21
  - iOS 15  
  
- **Language**
   - Android + Shared - [Kotlin](https://kotlinlang.org/)
   - IOS - [Swift](https://github.com/apple/swift)

- **UI Framework**
   - Android - [Jetpack Compose](https://developer.android.com/jetpack/compose)
   - IOS - [SwiftUI](https://developer.apple.com/xcode/swiftui/)
  
- **Architecture**
  - [MVVM Architecture](https://developer.android.com/topic/architecture) (Model - View - ViewModel)
  - [Repository Pattern](https://proandroiddev.com/the-real-repository-pattern-in-android-efba8662b754)
  - [Clean Code Architecture](https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576) (Usecase + mappers)
    
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Ktor](https://github.com/ktorio/ktor/tree/main): HTTP Client Franework for REST Apis.
- [SKIE](https://skie.touchlab.co): SKIE is a tool for Kotlin Multiplatform development that enhances the Swift API published from Kotlin.
- [KMMBridge](https://kmmbridge.touchlab.co): KMMBridge is a set of Gradle tooling that facilitates publishing and consuming pre-built KMM Xcode Framework binaries.
- [Kotlinx.serialization](https://github.com/square/moshi/): A modern JSON serialization library for Kotlin.
- [Coil](https://github.com/coil-kt/coil): Loading and caching images images from network (Android).
- [Kingfisher](https://github.com/onevcat/Kingfisher): Loading and caching images images from network (Ios).
- [Kermit](https://github.com/touchlab/Kermit): Multiplatform centralized logging utility.
- [Paging3](https://github.com/cashapp/multiplatform-paging): Multiplatform Paging Library
- [Multiplatform-Settings](https://github.com/russhwolf/multiplatform-settings): Multiplatform library for saving simple key-value data (SharedPreferences / NSUserDefaults)
- [BuildKonfig](https://github.com/yshrsmz/BuildKonfig): BuildConfig for Kotlin Multiplatform Project + Product Flavour in Shared Module
- [Koin](https://github.com/InsertKoinIO/koin) : A pragmatic lightweight dependency injection framework for Kotlin & Kotlin Multiplatform
- [Store](https://github.com/MobileNativeFoundation/Store) : A Kotlin Multiplatform library for building network-resilient applications (Build offline first apps)
- [SqlDelight](https://github.com/cashapp/sqldelight): Multiplatform SQLite Database (Generates typesafe Kotlin APIs from SQL)

## Features
- Playlist + Album Listing on HomePage
- Track listing inside playlist & album
- Paging Support in track listing screen using [Paging3](https://github.com/cashapp/multiplatform-paging)
- Loading / Error footer support while paging + retry functionlity in listing (supported in both android & ios)
- Offline first (Load from in-memory cache -> disk -> network) using [Store](https://github.com/MobileNativeFoundation/Store)
- Multi module support in android & shared modules
- Better support for suspend + flows + sealed classes using [SKIE](https://skie.touchlab.co)
- Publish IOS Binary as POD framework using [KMMBridge](https://kmmbridge.touchlab.co)
- BuildConfig + Product Flavour inside shared module using [BuildKonfig](https://github.com/yshrsmz/BuildKonfig)
- Dependency injection using [Koin](https://github.com/InsertKoinIO/koin)
- Database caching using [SqlDelight](https://github.com/cashapp/sqldelight)
- Access/Refresh Token functionality using [Ktor Auth Plugin](https://ktor.io/docs/bearer-client.html)

## Modularization

**Spotift-KMP** adopted modularization strategies below:

- **Reusability**: Modulizing reusable codes properly enable opportunities for code sharing and limits code accessibility in other modules at the same time.
- **Parallel Building**: Each module can be run in parallel and it reduces the build time.
- **Strict visibility control**: Modules restrict to expose dedicated components and access to other layers, so it prevents they're being used outside the module
- **Decentralized focusing**: Each developer team can assign their dedicated module and they can focus on their own modules.

**NOTE**: The same modularization strategies are used for [shared](https://github.com/AshuTyagi16/Spotify-KMP/tree/main/shared) module as well

For more information, check out the [Guide to app modularization](https://developer.android.com/topic/modularization).

## TODO
- Shared resource support using [moko-resources](https://github.com/icerockdev/moko-resources)
- MultiModule structure in iOS project


## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/AshuTyagi16/Spotify-KMP/stargazers)__ for this repository. :star: <br>
Also, __[follow me](https://github.com/AshuTyagi16)__ on GitHub for my next creations! 🤩

# License
```xml
Designed and developed by 2024 AshuTyagi16 (Ashu Tyagi)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
