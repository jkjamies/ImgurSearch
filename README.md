# Imgur Search Android Application

### Running Application

#### Unit Tests:

`./gradlew check`

#### UI Tests (omitted for time):

`./gradlew connectedAndroidTest`

#### Search Screen

| Dark Mode (Default)                         | Light Mode (Default)                            |
|---------------------------------------------|-------------------------------------------------|
| ![Search_Screen.png](docs/Idle_Dark.png)    | ![Search_Screen_Light.png](docs/Idle_Light.png) |
| Dark Mode (Loading)                         | Light Mode (Loading)                            |
| ![Search_Screen.png](docs/Loading_Dark.png) | ![Search_Screen.png](docs/Loading_Light.png)    |
| Dark Mode (Filter & Results)                | Light Mode (Filter and Results)                 |
| ![Search_Screen.png](docs/Filter_Dark.png)  | ![Search_Screen.png](docs/Filter_Light.png)     |

#### Details Screen

| Dark Mode                                  | Light Mode                                        |
|--------------------------------------------|---------------------------------------------------|
| ![Detail_Screen.png](docs/Detail_Dark.png) | ![Detail_Screen_Light.png](docs/Detail_Light.png) |

## Code Quality

### Project Results

![Qodana_Project_Results.png](docs/qodana.png)

## Improvement Ideas

- Table of single items in the SQLDelight DB rather than a list
- Use existing DB for filters for better performance
- Introduce concept of stale data to fetch remote data if local data is old
- Some empty files from "new project" were left, could clean up
- Address some TODO comments in the code representing better performance or ideas
- Add pagination for list of imgur images/gifs/videos
- Shared element transitions - click image and animate into full screen
- Accessibility enhancements
- Multiple languages
- Some error handling views for details screen
- Add more Unit tests
- Add UI tests
- Create documentation for implementation and architecture