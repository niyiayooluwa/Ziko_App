# Ziko

An offline-first Android phonetics and pronunciation training application built with Kotlin and Jetpack Compose. Ziko provides guided audiovisual drills across English monophthongs, diphthongs, triphthongs, and consonant articulation, featuring real-time speech evaluation and animated waveform visual feedback.

---

### Tech Stack & Architecture

| Layer | Technology | Purpose |
| --- | --- | --- |
| **Language & UI** | Kotlin, Jetpack Compose, Material 3 | Declarative UI, state hoisting, and dynamic system bar theming

 |
| **Architecture** | MVVM + Clean Architecture | Strict layer separation (Presentation $\rightarrow$ Domain $\rightarrow$ Data)

 |
| **Dependency Injection** | Dagger Hilt | Compile-time dependency injection across ViewModels, repositories, and services

 |
| **Asynchronous & Reactive** | Coroutines, Kotlin Flow, StateFlow | Asynchronous background execution and reactive UI state emissions

 |
| **Audio & Speech Engine** | Android `MediaPlayer`, `SpeechRecognizer` | On-device audio asset streaming and real-time speech-to-text recognition

 |
| **Local Persistence** | Jetpack DataStore Preferences | Asynchronous key-value caching for auth tokens, user data, and quiz metrics

 |
| **Networking** | Retrofit 2, Gson, OkHttp | REST API client for remote profile synchronization and score tracking

 |
| **Image Loading** | Coil Compose | Asynchronous image loading for remote and local profile media

 |

---

### Key Architectural Implementations

#### 1. Lifecycle-Safe Audio Pipeline

Managing audio playback inside a declarative UI tree often risks memory leaks or runaway background playback. Ziko isolates Android's native `MediaPlayer` within a centralized `AudioManager` singleton implementing `DefaultLifecycleObserver`:

* Binds to the Compose lifecycle via `DisposableEffect(lifecycleOwner)` across lesson screens, automatically pausing and releasing audio buffers on `onPause` and `onDestroy`.


* Employs an internal `MutableStateFlow<String?>` tracking `currentlyPlaying` asset paths to drive real-time play/pause toggle states across list items without recomposition overhead.



#### 2. Real-Time Speech Recognition & Decibel Waveform

Pronunciation practice uses Android’s native `SpeechRecognizer` wrapped in a custom `SpeechManager`:

* Avoids third-party SDK dependencies by tapping directly into `RecognitionListener.onRmsChanged(rmsdB)`.


* Clamps and projects raw audio input decibels into a 21-bar dynamic waveform animated smoothly via Compose's `animateDpAsState(tween(150))`.


* Normalizes speech results and expected phoneme phrases (stripping punctuation, casing, and whitespace) to deliver instantaneous phonetic validation.



#### 3. Offline-First Curriculum with SWR Caching

The application is structured to function completely without an active internet connection:

* Lessons and phonetic samples are bundled directly into compressed APK assets, loaded dynamically via `LessonDataProvider`.


* Remote assessment scores follow a stale-while-revalidate caching pattern inside `DataStoreManager`: cached scores are emitted instantly on launch, followed by a background network sync with a 5-minute invalidation window (`isAssessmentDataStale()`).



---

### Project Structure

```text
com.ziko/
├── core/
│   ├── common/         # Resource sealed classes for API state wrapping
│   ├── datastore/      # Preferences DataStore manager & serialization helpers
│   ├── di/             # Dagger Hilt modules (AppModule, ConnectivityModule)
│   ├── speech/         # Native SpeechRecognizer wrapper & RMS listeners
│   └── util/           # AudioManager, UI helpers, and string normalizers
├── data/
│   ├── local/          # Static lesson content & practice asset providers
│   ├── remote/         # Retrofit ApiService, request/response DTOs
│   └── repository/     # Auth & Assessment repository implementations
├── domain/
│   ├── model/          # Domain-level entities (AssessmentCardInfo, SignUpData)
│   ├── repository/     # Repository contracts (AuthRepository)
│   └── usecase/        # Isolated business logic use cases (AuthUseCase)
├── presentation/
│   ├── assessment/     # Quiz, MCQ, and speaking assessment screens & ViewModels
│   ├── auth/           # Login, registration, and credential validation flows
│   ├── components/     # Reusable UI widgets (SpeechButton, AudioButton, TopBars)
│   ├── home/           # Dashboard, lesson browsing, and topic drill selectors
│   ├── lesson/         # Guided phonetic lesson walkthroughs & audio listeners
│   ├── practice/       # Repeat-after-me speech practice screens
│   └── profile/        # User settings, security, and photo capture via MediaStore
└── ui/
    ├── model/          # Screen-level presentation models
    └── theme/          # Color schemes, typography, and theme composables

```

---

### Getting Started

#### Prerequisites

* Android Studio Ladybug (2024.2+) or newer
* JDK 17
* Android SDK 35 (Minimum SDK: 24)



#### Installation & Setup

1. Clone the repository:
```bash
git clone https://github.com/niyiayooluwa/ziko_app.git
cd ziko_app

```


2. Open the project in Android Studio.
3. Build the debug APK:
```bash
./gradlew assembleDebug

```


4. Run on a connected physical device or emulator with microphone support enabled to test speech recognition.



---

### Environment & Status Note

> **Note:** The backend staging environment for user profile authentication was decommissioned after client project delivery. The entire core learning loop—including curriculum walkthroughs, phonetics audio playback, on-device speech evaluation, and assessment drills—remains fully operational locally using bundled on-device assets.
> 
>
