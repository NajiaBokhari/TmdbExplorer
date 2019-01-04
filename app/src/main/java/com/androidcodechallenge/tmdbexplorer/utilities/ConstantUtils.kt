package com.androidcodechallenge.tmdbexplorer.utilities


object ConstantUtils {
    const val POPULAR = "popular"
    const val TOP_RATED = "top_rated"
    const val UPCOMING = "upcoming"
    const val LANGUAGE = "en-US"

    const val API_URL = "https://api.themoviedb.org"
    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"


    fun getPosterPath(posterPath: String): String {
        return BASE_POSTER_PATH + posterPath
    }

    fun getBackdropPath(backdropPath: String): String {
        return BASE_BACKDROP_PATH + backdropPath
    }

    const val APP_LOG = "TmdbExplorer"
    const val EXTRA_MOVIE = "movie"
}