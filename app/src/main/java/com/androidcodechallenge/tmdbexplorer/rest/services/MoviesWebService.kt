package com.androidcodechallenge.tmdbexplorer.rest.services

import com.androidcodechallenge.tmdbexplorer.rest.response.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesWebService {

    @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
    fun getMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("/3/search/movie?language=en-US")
    fun getMoviesByTitle(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<MovieResponse>
}