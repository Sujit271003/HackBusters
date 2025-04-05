# HectoClash - The Ultimate Mental Math Duel

HectoClash is an Android application that combines mental calculation challenges with real-time competitive gameplay. Players engage in head-to-head duels, solving Hectoc puzzles under time constraints.

## Features

- Real-time duels with other players
- Dynamic puzzle generation
- Time-based gameplay (60 seconds per duel)
- Leaderboard and rankings
- Educational insights and post-game analysis
- Spectator mode for watching ongoing duels

## Technical Stack

- Kotlin
- Jetpack Compose for UI
- MVVM Architecture
- Firebase Realtime Database
- Kotlin Coroutines for asynchronous operations
- Material3 Design System

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/hectoclash.git
   ```

2. Open the project in Android Studio (Arctic Fox or newer recommended)

3. Set up Firebase:
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Add an Android app to your Firebase project
   - Download the `google-services.json` file and place it in the `app` directory
   - Enable Realtime Database in your Firebase project

4. Update the application ID in `app/build.gradle.kts` if needed:
   ```kotlin
   defaultConfig {
       applicationId = "com.example.hectoclash"
       // ...
   }
   ```

5. Build and run the app on an Android device or emulator

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── hectoclash/
│   │   │               ├── data/
│   │   │               │   ├── model/
│   │   │               │   │   ├── Duel.kt
│   │   │               │   │   ├── HectocPuzzle.kt
│   │   │               │   │   └── User.kt
│   │   │               │   └── repository/
│   │   │               │       └── DuelRepository.kt
│   │   │               ├── presentation/
│   │   │               │   └── duel/
│   │   │               │       ├── DuelScreen.kt
│   │   │               │       ├── DuelViewModel.kt
│   │   │               │       └── DuelViewModelFactory.kt
│   │   │               ├── ui/
│   │   │               │   └── theme/
│   │   │               │       ├── Color.kt
│   │   │               │       ├── Theme.kt
│   │   │               │       └── Type.kt
│   │   │               ├── util/
│   │   │               │   └── HectocUtils.kt
│   │   │               ├── MainActivity.kt
│   │   │               └── HectoClashApp.kt
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
└── build.gradle.kts
```

## How to Play

1. Launch the app and start a new duel
2. Wait for an opponent to join
3. When the duel starts, you'll see a 6-digit sequence
4. Insert mathematical operations (+, -, *, /, ^, parentheses) between the digits to make the expression equal to 100
5. Submit your solution before the timer runs out
6. The player who submits a correct solution first wins

## Example

Given the sequence: 1 2 3 4 5 6

A valid solution: 1 + (2 + 3 + 4) × (5 + 6) = 100

## Contributing

Feel free to submit issues and enhancement requests!

## License

This project is licensed under the MIT License - see the LICENSE file for details. 