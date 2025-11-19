# AGENTS.md

This file provides precise guidance for AI coding agents working on this Android project. It covers setup, build/test commands, architecture conventions, security rules, and agent-specific guardrails.

## Project Overview

- Platform: Android (Kotlin)
- UI: Jetpack Compose
- Architecture: Clean Architecture, feature-based modules
- Presentation Pattern: UDF with MVI influences (unidirectional data flow, immutable state, side-effects isolated)
- DI: Koin
- Concurrency: Kotlin Coroutines + Flow
- Networking: Retrofit + OkHttp (if/when needed)
- JSON: Kotlin Serializable
- Persistence: Room; DataStore preferences
- Image Loading: Coil
- Build: Gradle (Kotlin DSL)
- Tests: JUnit 5, MockK, Turbine (for Flow), Robolectric (if UI unit tests are used)
- Linting/Static Analysis: ktlint, detekt, Android Lint

## Environment Setup

- Requirements:
    - Android Studio (latest stable)
    - JDK 17 (Temurin or compatible)
    - Android SDK, platforms, and build tools installed
    - Gradle wrapper is checked in; do not install a global Gradle

- Commands:
    - Sync/validate Gradle:
      ```
      ./gradlew tasks
      ```
    - Build debug APK:
      ```
      ./gradlew assembleDebug
      ```
    - Install on device/emulator:
      ```
      ./gradlew installDebug
      ```
    - Run unit tests (all modules):
      ```
      ./gradlew testDebugUnitTest
      ```
    - Android Lint:
      ```
      ./gradlew lint
      ```
    - ktlint check/format:
      ```
      ./gradlew ktlintCheck
      ./gradlew ktlintFormat
      ```
    - detekt:
      ```
      ./gradlew detekt
      ```

Before opening a PR, run:
```
./gradlew clean testDebugUnitTest ktlintCheck detekt lint
```

## Repository Structure & Conventions

Feature-based clean architecture. Typical top-level layout:

- domain/
    - model/ (pure domain entities)
    - repository/ (domain interfaces only)
    - usecase/ (UseCases; prefer `operator fun invoke()` entry point)
- data/
    - repository/ (implementations of domain repositories)
    - datasource/
        - remote/ (contracts + impls, e.g., `UserRemoteDataSource`, `RetrofitUserRemoteDataSource`)
        - local/ (contracts + impls, e.g., `UserLocalDataSource`, `RoomUserLocalDataSource`)
        - cache/ (if needed; in-memory or disk with TTL)
    - mapper/ (DTO/Entity <-> domain model)
    - model/ (DTOs, Room entities)
- presentation/
    - featureX/
        - ui/ (Compose screens, components)
        - state/ (immutable state, events/intents)
        - viewmodel/ (UDF/MVI reducers, effects via Coroutines/Flow)
- navigation/
- di/ (Koin modules: repositories, data sources, use cases, view models)

Rules:
- Domain never depends on Data or Presentation.
- UseCases depend only on domain contracts and models.
- Data repositories orchestrate data sources (remote/local/cache) and mapping.
- Avoid `Impl` suffix; prefer descriptive names like `DefaultUserRepository`, `OfflineFirstUserRepository`, `RetrofitUserRemoteDataSource`, `RoomUserLocalDataSource`, `PrefsUserLocalDataSource`.
- Secrets (keys) are treated as local source-of-truth and never exposed in cleartext outside Data.

## Use Case Conventions

Prefer `operator fun invoke()` as entry point:

```kotlin
class GetUserProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Result<UserProfile> =
        userRepository.getUserProfile(userId)
}
```

Naming:
- Boolean queries: `IsPasswordCreatedUseCase`, `IsPasswordSetUseCase`
- Actions: `CreateUserUseCase`, `SetPasswordUseCase`
- Orchestration: `ResolveStartupRouteUseCase` returning an enum like `StartupDestination`



## Presentation (UDF/MVI) Conventions

- Immutable State: Single source of truth per screen
- Unidirectional Flow: User actions → ViewModel → State update → UI recomposition
- Side Effects: Isolated in coroutines, never in state reducers
- StateFlow: ViewModel exposes state via `StateFlow`
- Compose Collection: UI collects state with `collectAsState()`

State structure:

```kotlin
data class FeatureState(
    val data: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedItem: Item? = null
)
```

Events/Intents:

```kotlin
sealed interface FeatureIntent {
    data object LoadData : FeatureIntent
    data class SelectItem(val id: String) : FeatureIntent
    data class UpdateItem(val item: Item) : FeatureIntent
}
```

ViewModel Pattern:

```kotlin
class FeatureViewModel(
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(FeatureState())
    val state: StateFlow<FeatureState> = _state.asStateFlow()
    
    fun onIntent(intent: FeatureIntent) {
        when (intent) {
            is FeatureIntent.LoadData -> loadData()
            is FeatureIntent.SelectItem -> selectItem(intent.id)
        }
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            getDataUseCase()
                .onSuccess { data ->
                    _state.update { it.copy(data = data, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(error = error.message, isLoading = false) }
                }
        }
    }
}
```

## Koin (DI)

- Define modules per layer/feature (e.g., `dataModule`, `domainModule`, `presentationModule`).
- Bind domain interfaces to concrete data implementations at the DI boundary.
- Example sketch (names are illustrative):

```kotlin
val dataModule = module {
    singleOf(::RetrofitUserRemoteDataSource) { bind<UserRemoteDataSource> }
    singleOf(::RoomUserLocalDataSource) { bind<UserLocalDataSource> }
    singleOf(::PrefsUserConfigLocalDataSource) { bind<UserConfigLocalDataSource> }
    singleOf(::DefaultUserRepository) { bind<UserRepository> }
}

val domainModule = module {
    factoryOf(::IsPasswordCreatedUseCase)
    factoryOf(::IsPasswordSetUseCase)
    factoryOf(::ResolveStartupRouteUseCase)
}

val presentationModule = module {
    viewModelOf(::LoginViewModel)
}
```

## Code Style

- Kotlin idiomatic; explicit visibility modifiers when helpful.
- Prefer expression-bodied functions for single expressions.
- Data classes for state/DTOs; sealed interfaces/classes for results and events.
- Coroutines: structured concurrency only; no `GlobalScope`.
- Error handling: use sealed results (`Result`, `Either`, or a project-specific `AppResult`).
- Compose: stateless UI components when possible; hoist state.
- No abbreviations like `UC` in type names; use `UseCase` suffix and descriptive verbs/nouns.

## Coroutines Best Practices

- Use `viewModelScope` in ViewModels
- Use `lifecycleScope` in Activities/Fragments
- Prefer `Flow` for streams, `suspend` for one-shots
- Use `StateFlow` for state, `SharedFlow` for events
- Always use structured concurrency

```kotlin
viewModelScope.launch {
    userRepository.getUser()
        .onSuccess { user -> /* handle */ }
        .onFailure { error -> /* handle */ }
}
```

## Compose Best Practices

- Keep composables stateless when possible
- Hoist state to the appropriate level
- Use `remember` for expensive computations
- Use `derivedStateOf` for computed state
- Apply `@Stable` and `@Immutable` for optimization

## Security

- Do not log or expose secrets in function signatures, exceptions, or analytics.
- Avoid backups for secret storage; ensure backup exclusion or custom BackupAgent configuration.
- Consider biometric gating (BiometricPrompt) for sensitive flows.
- Do not commit API keys or secrets. Use gradle properties/env vars.

## API Keys and Secrets

- Never commit secrets to version control
- Use `gradle.properties` or environment variables
- Access via `BuildConfig`

```kotlin
// build.gradle.kts
buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY")}\"")

// Usage
val apiKey = BuildConfig.API_KEY
```

## Network Security

- Use HTTPS only
- Implement certificate pinning for production
- Validate SSL certificates
- Use Network Security Config

## Testing

- Unit tests with JUnit 5 and MockK.
- Flow testing with Turbine when applicable.
- For pure reducers (MVI), prefer deterministic unit tests on state transitions.
- Command examples:
  ```
  ./gradlew testDebugUnitTest
  ./gradlew :feature-login:testDebugUnitTest
  ```
- Add/update tests for changed logic; keep coverage for business logic high.

## Unit Tests

```kotlin
class GetUserUseCaseTest {
    
    private val repository = mockk<UserRepository>()
    private val useCase = GetUserUseCase(repository)
    
    @Test
    fun `should return user when repository succeeds`() = runTest {
        // Given
        val user = User("1", "John")
        coEvery { repository.getUser("1") } returns Result.success(user)
        
        // When
        val result = useCase("1")
        
        // Then
        assertEquals(Result.success(user), result)
    }
}
```

## Flow Testing with Turbine

```kotlin
@Test
fun `should emit loading then success states`() = runTest {
    viewModel.state.test {
        // Initial state
        assertEquals(FeatureState(), awaitItem())
        
        // Trigger action
        viewModel.onIntent(FeatureIntent.LoadData)
        
        // Loading state
        assertEquals(FeatureState(isLoading = true), awaitItem())
        
        // Success state
        val successState = awaitItem()
        assertFalse(successState.isLoading)
        assertTrue(successState.data.isNotEmpty())
    }
}
```

## Lint & Static Analysis

- Run before PR:
  ```
  ./gradlew ktlintCheck detekt lint
  ```
- Keep code formatted with ktlint; run `ktlintFormat` to auto-fix.

## Module/Task Scoping

- Run tasks per module:
  ```
  ./gradlew :app:assembleDebug
  ./gradlew :feature-login:testDebugUnitTest
  ```

## Agent Guardrails

- Never print, persist, or transmit private keys.
- Do not move security logic from Data into Presentation.
- Do not weaken encryption or remove error checks.
- Prefer adding to existing abstractions over introducing duplicates.
- Preserve package structure and naming conventions.
- After Gradle or dependency changes, always run build and tests.

## Common Issues

- JDK mismatch:
    - Ensure JDK 17 and set Gradle toolchain:
      ```kotlin
      kotlin {
          jvmToolchain(17)
      }
      ```
- Android SDK errors:
    - Ensure required platforms/build-tools are installed; set `ANDROID_HOME`.
- Flaky coroutine tests:
    - Use `runTest` and a `StandardTestDispatcher`; avoid real delays.

## Coroutine Test Issues

Use `runTest` and `StandardTestDispatcher`:

```kotlin
@Test
fun test() = runTest {
    // Test code
}
```

## Compose Recomposition Issues

- Use `remember` for expensive operations
- Apply `@Stable` and `@Immutable` annotations
- Use `derivedStateOf` for computed values
- Avoid creating new lambdas in composables

## Full Verification Pipeline

```
./gradlew clean assembleDebug testDebugUnitTest ktlintCheck detekt lint
```

## Module Structure (Multi-Module Projects)

For larger projects, consider feature modules:

```
:app                   # Application module
:core:network          # Shared network utilities
:core:database         # Shared database utilities
:core:designsystem     # Shared UI components
:core:analytics        # Shared analytics utilities
:feature:home          # Home feature module
:feature:settings      # Settings feature module
```

Each feature module follows the same Clean Architecture structure internally.

## Additional Resources

- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Jetpack Compose Guidelines](https://developer.android.com/jetpack/compose/mental-model)
- [Koin Documentation](https://insert-koin.io/)
