# Todo App

TODO List App
This is a simple TODO list application built using Kotlin and Jetpack Compose. It allows users to
add, search, and manage their TODO items. The app follows modern Android development practices
including MVVM architecture, Kotlin Coroutines, Room Database for local storage, and integrates
error handling for a smooth user experience.

# **Features**

### Main Screen:

* Displays a list of TODO items.
* Supports filtering with a search field.
* Shows a prompt if the list is empty.

### Add New Item:

* Accessed via a floating action button on the main screen.
* Allows users to add new TODO items.
* Implements loading indicators and feedback on successful addition.

## Error Handling:

* Displays appropriate error messages when adding TODO items fails.
* Handles exceptions gracefully, ensuring smooth navigation and user feedback.

## State Management:

* Maintains application state across screen rotations.
* Uses MVVM architecture with Kotlin Flows for reactive updates.

## Data Persistence:

* Utilizes Room Database to persist TODO items locally.
* Ensures data integrity and reliability.

# **Screenshots**

Part 1: TODO Creation

This video shows the process of creating a TODO item, detailing the main screen layout and the
steps to add new items.

[Watch Part 1](https://github.com/vjayrajput/TodoAppJetpackComposeMVVM/assets/11531822/85509aa8-bd82-431e-be05-c105e3241cc8)

Part 2: TODO Creation Error Handling


This video illustrates the error handling mechanism in action, demonstrating what happens when an
error occurs while adding a TODO item.

[Watch Part 2](https://github.com/vjayrajput/TodoAppJetpackComposeMVVM/assets/11531822/e1e64626-fe63-476d-8e67-4911357099b9)

Part 3: Handling Application State in Various Scenarios

This video explains how the app manages states across different scenarios, including screen
rotations and navigation changes.

[Watch Part 3](https://github.com/vjayrajput/TodoAppJetpackComposeMVVM/assets/11531822/59e8a3c0-0f68-41a1-8e63-32147b156a15)

## Technologies Used

* Kotlin - Main programming language.
* Jetpack Compose - Modern UI toolkit for building native Android UI.
* Room Database - Local storage solution for persisting TODO items.
* Coroutines - For asynchronous programming and managing background tasks.
* MVVM Architecture - Separation of concerns for cleaner and more maintainable code.
* Error Handling - Implemented to enhance user experience and provide meaningful feedback.

## License

* This project is licensed under the MIT License - see the LICENSE file for details.
