# Powdermills-Android

Powdermills is an app available on Android and iOS that allows users to view information on, and navigate around the ruins of the Gunpowder Mill at Ballincollig Regional Park

## Build Steps
1. Clone the repo to your local development machine

2. Add the mapbox token to your __gradle.properties__ file (Local or Global) using the key below, this allows us to pull down the mapbox dependency and to pull down our mapbox styles at runtime:

```groovy
MAPBOX_DOWNLOADS_TOKEN=****
```

3. Place the google-services.json file in the app, this facilitates and authenticates our connection firebase for data and image storage

4. Build and run the app, you're good to go!

## Architecture

Powdermills is written in Kotlin in an MVVM architecture and uses a repository pattern to decouple the network and data layers from the UI.
Simple persistence is achieved using Shared Preferences and Datastore. Streams are achieved via coroutine flows.

## Libraries
Persistence: Shared Preferences,Datastore
Mapping: Mapbox
Carousel: Carouselview
Image Loading: Glide
Backend: Firebase
Dependency Injection: Hilt

## Contributing
If you'd like to contribute send an email to: powdermillsballincollig@gmail.com with the subject "Powdermills Android Contribution Request".
We'll get back to you with required config files and keys to set up the project and access to our Slack and Trello board.

## Testing
There is not currently any tests for the app, these will be added in future PRs, all new PRs should have test coverage written for them.

## Release Builds
Release builds are run internally.

## License
[MIT](https://choosealicense.com/licenses/mit/)
