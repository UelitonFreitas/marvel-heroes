# marvel-heroes


# Set me up

## Environment Variables

Add Marvel API KEY and Private API KEY as environment variables:
```bash
# MARVEL API
export MARVEL_API_KEY="your_api_key"
export MARVEL_PRIVATE_API_KEY="your_private_api_key"
```


## Test coverage of business logic
### Unit tests
MainScreenPresenter	100%
CharacterDetailsPresenter 100%

## To Do
A lot of things can be done. You can find some of then here:
- Support offline mode
  Create a Cache repository that receives the remote and a persistent, with Room, for example. When recovering from API save in Room and return persisted data. 
- Add a Dependency Injection framework like Dagger2 if needed.
- Optimize app layout and resources
- Create async character load in Character Detail Screen. 
It is possible to get the character from the repository and use deep links for better code encapsulations.
- Fix recycler view bug when a page is loaded and the screen comes back for the beginning of the activity.
Besides Retrofit makes the request out of Main Thread, it is possible to use Coroutines.
- Create better navigation from details view to character list view.
- Add expresso tests using persisted data to create integration tests of UI and contract tests. We can use different flavors for this objective.
- Automatic upload of APK to store using Kotlin DSL scripts or Fastlane.