<h1 align="center">Spotify-KMP</h1>


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

## Modularization

**Spotift-KMP** adopted modularization strategies below:

- **Reusability**: Modulizing reusable codes properly enable opportunities for code sharing and limits code accessibility in other modules at the same time.
- **Parallel Building**: Each module can be run in parallel and it reduces the build time.
- **Strict visibility control**: Modules restrict to expose dedicated components and access to other layers, so it prevents they're being used outside the module
- **Decentralized focusing**: Each developer team can assign their dedicated module and they can focus on their own modules.

**NOTE**: The same modularization strategies are used for shared module as well

For more information, check out the [Guide to app modularization](https://developer.android.com/topic/modularization).

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
