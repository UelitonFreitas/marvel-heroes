# marvel-heroes


# Set me up

## Environment Variables

Add Marvel API KEY and Private API KEY as environment variables in your machine:
```bash
# MARVEL API
export MARVEL_API_KEY="your_api_key"
export MARVEL_PRIVATE_API_KEY="your_private_api_key"
```



## Test coverage
### Unit tests
MainScreenPresenter	100%
CharacterDetailsPresenter 100%

### Integration tests
For integration tests User Journey paradigm was used. You can run with command below, or in `CharacterViewUserJourneyTest` class:
```bash
./gradlew connectedAndroidTest
```

## To Do
A lot of things can be done. You can find some of then here:
- A UX person with good heath to create a descent layout :)
- Support offline mode.
  Create a Cache repository that receives the remote and a persistent(Room or other). When recovering from API save in persistent repository and return persisted data. 
- Add a Dependency Injection framework like Dagger2 if needed.
- Optimize app layout and resources
- Create async character load in Character Detail Screen. 
It is possible to get the character from the repository and use deep links for better code encapsulations.
- Fix recycler view bug when a page is loaded and the screen comes back for the beginning of the activity.
Besides Retrofit makes the request out of Main Thread, it is possible to use Coroutines.
- Create better navigation from details view to character list view.
- Add more Espresso tests using persisted data to create integration tests of UI and contract tests. We can use different flavors for this objective.
- Automatic upload of APK to store using Kotlin DSL scripts or Fastlane.
- Create appropriated errors and stop using Exceptions.