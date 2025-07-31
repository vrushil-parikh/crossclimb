
# 🧗‍♂️ Crossclimb - Educational Puzzle Game

An Android educational puzzle game built with Jetpack Compose and Kotlin, featuring vertical climbing word puzzles that reinforce curriculum topics.

## 📱 App Overview

Crossclimb is an engaging educational game where players solve clues to climb through 6 rows of puzzles. Each correct answer unlocks the next row, creating a satisfying progression through educational content like World History and Biology vocabulary.

### 🎮 Core Features

- **Progressive Puzzle Solving**: 6-row vertical puzzle format with unlock progression
- **Educational Themes**: Multiple puzzle sets covering curriculum topics
- **Hint System**: Get help with 5-second timer penalty
- **Reveal System**: Complete difficult rows (locks score for that puzzle)
- **Timer & Scoring**: Track completion time and calculate scores
- **Accessibility**: Full TalkBack support and dynamic font sizing
- **Theme Support**: Dark/Light mode switching
- **Score Sharing**: Share achievements via Android Sharesheet

## 🏗️ Architecture

### Clean Architecture with MVVM Pattern

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   UI Layer      │    │ Presentation    │    │   Domain        │
│ (Jetpack       │◄───┤    Layer        │◄───┤    Layer        │
│  Compose)       │    │  (ViewModels)   │    │ (Use Cases)     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        ▲
                                               ┌─────────────────┐
                                               │   Data Layer    │
                                               │ (Repository +   │
                                               │ Local Storage)  │
                                               └─────────────────┘
```

### 📁 Project Structure

```
app/
├── src/main/kotlin/com/yourname/crossclimb/
│   ├── di/                     # Hilt Dependency Injection
│   │   ├── AppModule.kt
│   │   ├── DataModule.kt
│   │   └── DomainModule.kt
│   │
│   ├── data/
│   │   ├── local/
│   │   │   ├── PuzzleDataSource.kt
│   │   │   └── PreferencesManager.kt
│   │   ├── repository/
│   │   │   └── PuzzleRepositoryImpl.kt
│   │   └── model/
│   │       ├── PuzzleDto.kt
│   │       └── GameStateDto.kt
│   │
│   ├── domain/
│   │   ├── model/
│   │   │   ├── Puzzle.kt
│   │   │   ├── Row.kt
│   │   │   ├── GameState.kt
│   │   │   └── Score.kt
│   │   ├── repository/
│   │   │   └── PuzzleRepository.kt
│   │   └── usecase/
│   │       ├── ValidateAnswerUseCase.kt
│   │       ├── GetHintUseCase.kt
│   │       ├── CalculateScoreUseCase.kt
│   │       ├── TimerUseCase.kt
│   │       └── LoadPuzzleUseCase.kt
│   │
│   ├── presentation/
│   │   ├── ui/
│   │   │   ├── game/
│   │   │   │   ├── GameScreen.kt
│   │   │   │   ├── RowComponent.kt
│   │   │   │   └── TimerComponent.kt
│   │   │   ├── selection/
│   │   │   │   └── PuzzleSelectionScreen.kt
│   │   │   ├── score/
│   │   │   │   └── ScoreScreen.kt
│   │   │   ├── components/
│   │   │   │   ├── HintButton.kt
│   │   │   │   ├── RevealButton.kt
│   │   │   │   └── InputField.kt
│   │   │   └── theme/
│   │   │       ├── Theme.kt
│   │   │       ├── Color.kt
│   │   │       └── Type.kt
│   │   └── viewmodel/
│   │       ├── GameViewModel.kt
│   │       └── PuzzleSelectionViewModel.kt
│   │
│   ├── utils/
│   │   ├── Constants.kt
│   │   └── Extensions.kt
│   │
│   └── MainActivity.kt
│
├── src/main/assets/
│   └── puzzle_sets.json         # Puzzle data storage
│
├── src/test/                    # Unit Tests
│   ├── domain/usecase/
│   ├── presentation/viewmodel/
│   └── data/repository/
│
└── src/androidTest/             # UI Tests
    └── presentation/ui/
```

## 🎯 Key Components

### Data Models

```kotlin
data class Puzzle(
    val id: String,
    val title: String,
    val theme: String,
    val rows: List<Row>
)

data class Row(
    val id: Int,
    val clue: String,
    val answer: String,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val revealedLetters: Int = 0,
    val userInput: String = ""
)

data class GameState(
    val currentPuzzle: Puzzle?,
    val currentRowIndex: Int = 0,
    val startTime: Long = 0L,
    val elapsedTime: Long = 0L,
    val hintsUsed: Int = 0,
    val rowsRevealed: Int = 0,
    val isGameCompleted: Boolean = false,
    val score: Int = 0
)
```

### Use Cases (Business Logic)

- **ValidateAnswerUseCase**: Validates user input against correct answers
- **GetHintUseCase**: Reveals next letter with 5-second timer penalty
- **CalculateScoreUseCase**: Computes final score based on time, hints, and reveals
- **TimerUseCase**: Manages stopwatch functionality during gameplay
- **LoadPuzzleUseCase**: Loads and parses puzzle sets from JSON

## 📊 Puzzle Data Format

### JSON Structure
```json
{
  "puzzleSets": [
    {
      "id": "world_history_1",
      "title": "Ancient Civilizations",
      "theme": "World History",
      "rows": [
        {
          "id": 1,
          "clue": "Ancient Egyptian burial structure with triangular sides",
          "answer": "PYRAMID"
        },
        {
          "id": 2,
          "clue": "Greek city-state known for military discipline",
          "answer": "SPARTA"
        }
        // ... 4 more rows
      ]
    },
    {
      "id": "biology_1",
      "title": "Cell Biology Basics",
      "theme": "Biology",
      "rows": [
        {
          "id": 1,
          "clue": "Powerhouse of the cell that produces ATP",
          "answer": "MITOCHONDRIA"
        }
        // ... 5 more rows
      ]
    }
  ]
}
```

### Adding New Puzzle Sets

1. Open `src/main/assets/puzzle_sets.json`
2. Add new puzzle object to the `puzzleSets` array
3. Follow the established JSON structure
4. Ensure answers are in UPPERCASE
5. Test the new puzzle set in the app

**Requirements for puzzle creation:**
- Each set must have exactly 6 rows
- Answers should be single words or phrases (no spaces)
- Clues should be clear and educational
- Maintain consistent difficulty progression

## 🧪 Testing

### Running Tests

```bash
# Unit Tests
./gradlew test

# UI Tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# All Tests
./gradlew check
```

### Test Coverage

- **Unit Tests**: Business logic, ViewModels, Use Cases, Repository
- **UI Tests**: User interaction flows, accessibility features
- **Integration Tests**: End-to-end puzzle completion scenarios

### Key Test Scenarios

**Happy Path:**
- Complete puzzle without hints
- Timer accuracy
- Score calculation
- Row unlocking progression

**Edge Cases:**
- Hint usage with timer penalties
- Row reveal functionality
- Invalid input handling
- Configuration changes

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 24+ (target SDK 34)
- Kotlin 1.9.0+

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd crossclimb-android
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on device/emulator**
   - Connect Android device or start emulator
   - Click "Run" in Android Studio or use `./gradlew installDebug`

## 🎨 UI/UX Features

### Accessibility
- **TalkBack Support**: Full screen reader compatibility
- **Dynamic Text**: Supports system font size changes
- **High Contrast**: Compatible with accessibility display settings
- **Focus Management**: Proper focus handling for keyboard navigation

### Visual Feedback
- **Row States**: Clear visual indicators (locked, active, completed)
- **Input Validation**: Real-time feedback for correct/incorrect answers
- **Progress Animation**: Smooth transitions between states
- **Theme Support**: Automatic dark/light mode switching

### User Experience
- **Intuitive Controls**: Tap to activate rows, simple input fields
- **Progress Tracking**: Visual completion indicators and timer display
- **Score Sharing**: Easy sharing of achievements via Android Sharesheet
- **Persistent Progress**: Best times saved locally

## 📈 Scoring System

```kotlin
Score = BaseScore - (HintsUsed * 50) - (RowsRevealed * 100) - (ElapsedSeconds * 2)

Where:
- BaseScore = 1000 points
- Each hint costs 50 points + 5 seconds penalty
- Each revealed row costs 100 points
- Each second reduces score by 2 points
- Minimum score is 0
```

## 🔧 Configuration

### Build Variants
- **Debug**: Development build with logging enabled
- **Release**: Production build with optimizations

### Dependencies
- Jetpack Compose (UI framework)
- Hilt (Dependency injection)
- Coroutines (Asynchronous programming)
- StateFlow (Reactive state management)
- JUnit/Espresso (Testing frameworks)

## 🚧 Known Issues & Future Enhancements

### Current Limitations
- Local storage only (no cloud sync)
- Limited to 6 rows per puzzle
- Basic sharing functionality

### Planned Features
- [ ] Cloud synchronization
- [ ] Multiplayer challenges
- [ ] Custom puzzle creation
- [ ] Achievement system
- [ ] Statistics dashboard

## 🤝 Contributing


**Built with ❤️ using Kotlin and Jetpack Compose**
