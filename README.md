# Which-Movie
Android app that gets a random IMDb movie.

**Random Movie Picker Project**
---
Makes use of OMDb (The Open Movie Database) API which is a RESTful web service to obtain movie information.

**Prerequisites**
---
Min SDK version: 19.

Targeted & Compiled SDK version: 27.

Requires permission "Internet Access".

You **MUST** use your own API key when sending data requests in this format: `http://www.omdbapi.com/?apikey=[yourkey]&`
in HttpClient here: private static String apiKey = "[yourKey]";

You can get your own API key here: http://www.omdbapi.com/apikey.aspx

**Built with**
---
Language - Java.

Environment - Android Studio.

Dependencies - Gson & OkHttp.

**Tested on**
---
Nougat (7.1.0).

**Author**
---
Sam McKnight

**Acknowledgments**
---
OMDb API by Brian Fritz @ http://www.omdbapi.com/
