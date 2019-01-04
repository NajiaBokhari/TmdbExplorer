package com.androidcodechallenge.tmdbexplorer.storage

import com.androidcodechallenge.tmdbexplorer.models.Movie
import io.paperdb.BuildConfig
import io.paperdb.Paper

class PreferenceStorage : TokenStorage {

    companion object {
        const val KEY_MOVIE = BuildConfig.APPLICATION_ID + "KEY_MOVIE"
    }

    override fun setMovie(movie: Movie) {
        Paper.book().write(KEY_MOVIE, movie)
    }

    override fun getMovie(): Movie? {
        return if (Paper.book().contains(KEY_MOVIE)) {
            Paper.book().read<Movie>(KEY_MOVIE)
        } else {
            null
        }
    }

    override fun clearDatabase() {
        Paper.book().destroy()
    }
}