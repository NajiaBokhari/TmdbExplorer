package com.androidcodechallenge.tmdbexplorer.rest.response

import com.androidcodechallenge.tmdbexplorer.models.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var items: List<Movie>,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("total_pages") var totalPage: Int
)