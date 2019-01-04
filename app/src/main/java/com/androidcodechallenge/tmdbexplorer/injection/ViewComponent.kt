package com.androidcodechallenge.tmdbexplorer.injection

import com.androidcodechallenge.tmdbexplorer.ui.movies.MoviesActivity
import com.androidcodechallenge.tmdbexplorer.ui.movies.detail.MovieDetailActivity
import com.androidcodechallenge.tmdbexplorer.ui.movies.listing.MoviesListingFragment
import dagger.Subcomponent

@ViewScope
@Subcomponent(modules = [(ViewModule::class)])
interface ViewComponent {
    fun inject(activity: MoviesActivity)
    fun inject(fragment: MoviesListingFragment)
    fun inject(activity: MovieDetailActivity)

}
