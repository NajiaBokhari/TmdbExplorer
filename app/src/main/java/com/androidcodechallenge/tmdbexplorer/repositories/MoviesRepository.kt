package com.androidcodechallenge.tmdbexplorer.repositories

import com.androidcodechallenge.tmdbexplorer.rest.response.MovieResponse
import com.androidcodechallenge.tmdbexplorer.rest.services.MoviesWebService
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesWebService: MoviesWebService) : BaseRepository() {

    fun loadMoviesByTitle(status: String, currentPage: Int): Single<MovieResponse> {
        return moviesWebService.getMoviesByTitle(status, currentPage).compose(applySchedulers())
    }

    fun loadMoviesList(currentPage: Int): Single<MovieResponse> {
        return moviesWebService.getMovies(currentPage).compose(applySchedulers())
    }
}