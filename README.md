# Challenge Login Fake

An android application to create a ```Login Fake```
using clean architecture for separation of code in different layers with assigned responsibilities in order to make it easier for further modification.

## Installation

Just one step to clone the repo as below


```bash
git clone https://github.com/gibranhit/FakeLoginTest
```

It's recommend to have the latest version of android studio **Flamingo | 2022.2.1**

## Project structure

As we mentioned before we're taking into account SOLID principles and clean architecture and of course clean code. The project structure contains the following layers:

1. **UI (Presentation)**
    - Includes a reference of the domain and data layer. It's an android specific layer which executes the UI logic. This layer also contains the implementation of the architectural pattern which is **MVVM** as required in the code challenge.

2. **Domain**
    - It is just a pure kotlin package with no android specific dependency. It contains the execution of the business rules (use case) .
    - Contains the definition of the business entities.

3. **Data**
    - Provides the required data for the application to the domain layer by implementing interfaces (**repositories**).
    - This layer is basically contains the implementation for the remote data source (**retrofit**)

## Unit tests

Unit tests are located at ```test``` and ```androidTest```directories.

**Test directory**
- Contains unit tests for the **viewModel**, **repository** and **useCase**

**Android test directory**
- Contains ui test for the **view**. These test must be run using a device or an emulator.


## Build with

* [Kotlin](https://kotlinlang.org/) - Main programming language
* [MVVM](https://developer.android.com/jetpack/guide) - Architectural pattern used
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Framework for Dependency Injection
* [Retrofit](https://square.github.io/retrofit/) - To make network requests
* [Sandwich](https://github.com/skydoves/sandwich) - To modeling Retrofit response and handling exceptions
* [Coroutines](https://developer.android.com/kotlin/coroutines) - To handle async calls
* [Jetpack](https://developer.android.com/jetpack?hl=es-419) - Used in conjunction with MVVM
* [Compose](https://developer.android.com/jetpack/compose?hl=es-419) - To design ui with less code

## Demo app


## Author

Hector Gibran Reyes Alvarez
* [Linkedin](https://www.linkedin.com/in/gibran-reyes-429992171/)