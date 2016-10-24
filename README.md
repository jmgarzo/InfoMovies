# InfoMovies
## Synopsis
Final project for the course [Developing Android Apps](https://www.udacity.com/course/developing-android-apps--ud853)

##Project guideline
###1. User Interface - Layout
- Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails
- UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated, and favorites
- UI contains a screen for displaying the details for a selected movie
- Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
- Movie Details layout contains a section for displaying trailer videos and user reviews
- Tablet UI uses a Master-Detail layout implemented using fragments. The left fragment is for discovering movies. The right fragment displays the movie details view for the currently selected movie.

###2. User Interface - Function
- When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
- When a movie poster thumbnail is selected, the movie details screen is launched [Phone] or displayed in a fragment [Tablet]
- When a trailer is selected, app uses an Intent to launch the trailer
- In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite

###3. Network API Implementation
- In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.
- App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.
- App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.

###4. Data Persistence
- App saves a “Favorited” movie to SharedPreferences or a database using the movie’s id.
- When the “favorites” setting option is selected, the main view displays the entire favorites collection based on movie IDs stored in SharedPreferences or a database.

###5. ContentProvider
- App persists favorite movie details using a database
- App displays favorite movie details (title, poster, synopsis, user rating, release date) even when offline
- App uses a ContentProvider to populate favorite movie details

###6. Sharing Functionality
- Movie Details View includes an Action Bar item that allows the user to share the first trailer video URL from the list of trailers
- App uses a share Intent to expose the external youtube URL for the trailer

##Motivation
This project exist as a part of my professional portfolio about Android

In this project I have learned:

- Build and deploy apps on Android devices
- Present and use network resources in your apps
- Navigate between multiple screens in an app
- Create databases and use Content Providers to manage access to your data
- Create responsive user interfaces that work across a variety of screen sizes(Phones and tablet)
- Keep your app running efficiently with the Use of AsyncTasks and Loaders

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact