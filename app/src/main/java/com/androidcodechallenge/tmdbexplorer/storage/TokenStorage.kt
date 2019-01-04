package com.androidcodechallenge.tmdbexplorer.storage

import com.androidcodechallenge.tmdbexplorer.models.Movie

interface TokenStorage {

    fun setMovie(user: Movie)

    fun getMovie(): Movie?

    fun clearDatabase()

}