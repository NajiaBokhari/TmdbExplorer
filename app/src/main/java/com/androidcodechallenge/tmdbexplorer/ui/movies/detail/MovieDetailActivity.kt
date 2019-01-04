package com.androidcodechallenge.tmdbexplorer.ui.movies.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.ViewModelProviders
import com.androidcodechallenge.tmdbexplorer.R
import com.androidcodechallenge.tmdbexplorer.injection.ViewModelFactory
import com.androidcodechallenge.tmdbexplorer.models.Movie
import com.androidcodechallenge.tmdbexplorer.ui.base.BaseActivity
import com.androidcodechallenge.tmdbexplorer.utilities.ConstantUtils
import com.androidcodechallenge.tmdbexplorer.viewmodels.MovieViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.layout_movie_detail_header.*
import javax.inject.Inject


class MovieDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MovieViewModel


    override fun inject() {
        component.inject(this)
    }

    companion object {

        fun getInstance(context: Context?, movie: Movie): Intent {
            return Intent(context, MovieDetailActivity::class.java)
                .putExtra(ConstantUtils.EXTRA_MOVIE, movie)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java)

        viewModel.setMovieDetail(intent.getParcelableExtra<Parcelable>(ConstantUtils.EXTRA_MOVIE) as Movie)

        setUpAttributeValues(viewModel.getMovieDetail())

    }


    private fun setUpAttributeValues(movie: Movie?) {
        setAppBarDimensions(activity_movie_detail_toolbar)
        movie?.let {

            setToolbarHomeIndicator(activity_movie_detail_toolbar, it.title)
            it.backdrop_path?.let {
                Glide.with(this).load(ConstantUtils.getBackdropPath(it))
                    .listener(requestGlideListener(activity_movie_detail_poster))
                    .into(activity_movie_detail_poster)
            } ?: let {
                Glide.with(this).load(ConstantUtils.getBackdropPath(movie.poster_path!!))
                    .listener(requestGlideListener(activity_movie_detail_poster))
                    .into(activity_movie_detail_poster)
            }
            layout_movie_detail_header_title.text = it.title
            layout_movie_detail_header_release.text = getString(R.string.releasing_date_txt) + " " + it.release_date
            layout_movie_detail_header_rating.rating = (it.vote_average / 2).toFloat()
            layout_movie_detail_header_vote.text = it.vote_count.toString()
            layout_movie_detail_header_revenue.text = viewModel.setMissingValue(this, it.revenue, true)
            layout_movie_detail_header_duration.text = viewModel.setMissingValue(this, it.runtime, false)
            layout_detail_overview_summary.text = it.overview
        }
    }

}
