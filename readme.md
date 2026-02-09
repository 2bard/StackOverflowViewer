# StackOverflowViewer

## Technologies
- Modern MVVM structure using ViewModel/UseCase/StateFlow/SavedStateHandle
- Hilt for DI
- LocalData for local storage
- Navigation 2 for nav graph
- Retrofit/okhttp/moshi for network requests
- Robolectric + Mockk + Paparazzi for tests

- AGP 8.10.1
- Built using Android Studio 2024.3.2 - runs quickly on my machine!

## Decisions made

### App
- LocalData was used as Room felt like overkill for storing a list of followers
- Navigation 2 lib used as I've not used Nav 3 yet
- Hilt was used as it's quick and easy to get started with
- Retrofit - pretty standard though there's a debate about whether Ktor should be used going forward...
- MVVM - following standard practices, nothing too out of the ordinary going on here
- There's a usersAndFollows StateFlow which combines a users and follows StateFlow - this was used to fit the requirement for a separate Users and Follows data source 
- There is no Repository layer offline caching yet - Room would be best for that 
- SharedFlow used to emit errors and follow events
- SavedStateHandle is used for UI layer caching
- UI is super basic. Default colors have been used.
- I've tried to avoid hard coding anything UI related, most dimensions are in Theme.kt.

### Testing
- I've not added any UseCase tests as the UseCases are super simple at the moment
- Paparazzi was used for Snapshot testing - super easy to get running and very flexible
- Mockk for mocking - seems like the natural successor to Mockito, more kotlin idiomatic

## Future improvements
- Nicer UI. Add padding etc to CompositionLocal to integrate with MaterialTheme
- Add Room for proper repository level caching
- UI state could be made into its own class which includes an ErrorState - though this would make SavedStateHandle more complex
- Instrumentation tests 
- Snapshot tests could be greatly expanded
- Update to kotlin 2.3.x to take advantage of explicit backing fields

## Build artifacts
- Paparazzi report is at build/reports/paparazzi/debug/index.html

# Paparazzi screenshots

## User list - Normal text

![User list - Normal text](app/build/reports/paparazzi/debug/images/0010acf838a60e9c8c6e388ee8084225f0b4fd3e.png)

## User list - Large text

![User list - Large text](app/build/reports/paparazzi/debug/images/551b37bbef4213b6aea0c1f2515d84cf44196c4c.png)

## User list - Extra Large text

![User list - Extra Large text](app/build/reports/paparazzi/debug/images/d1797b7ef34b28f825fd20a675e145dd3ceabab2.png)

## User list - Loading - Normal text

![User list - Loading - Normal text](app/build/reports/paparazzi/debug/images/acb40edf070fc83f9b837c7db061795e6818e250.png)

## User list - Loading - Large text

![User list - Loading - Large text](app/build/reports/paparazzi/debug/images/3e607bb1a1d2d16fcc40426716b70f59d00c074e.png)

## User list - Loading - Extra Large text

![User list - Loading - Extra Large text](app/build/reports/paparazzi/debug/images/47168f2119cf282939fed8f6599e03cd201a231f.png)

## User list - Empty list - Normal text

![User list - Empty list - Normal text](app/build/reports/paparazzi/debug/images/7c199e0531bb5e7e3c703521a14493589f9bcf75.png)

## User list - Empty list - Large text

![User list - Empty list - Large text](app/build/reports/paparazzi/debug/images/889b2e0beea1a6ab4dd46e2b88586ddd65b6e6ec.png)

## User list - Loading - Extra Large text

![User list - Empty list - Extra Large text](app/build/reports/paparazzi/debug/images/4eb913527db8ab7b8ed7630f6e8ed926eb64ea75.png)

# Demo videos

## Loading users
[[Video - demo 1]](video/video-normal.mov)

## Empty list state + error message
[[Video - demo error]](video/video-error.mov)