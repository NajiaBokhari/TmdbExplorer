package com.androidcodechallenge.tmdbexplorer.ui.movies.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidcodechallenge.tmdbexplorer.R
import com.androidcodechallenge.tmdbexplorer.models.Movie
import com.androidcodechallenge.tmdbexplorer.rest.ApiResponseResource
import com.androidcodechallenge.tmdbexplorer.rest.Status
import com.androidcodechallenge.tmdbexplorer.ui.base.BaseFragment
import com.androidcodechallenge.tmdbexplorer.ui.movies.detail.MovieDetailActivity
import com.androidcodechallenge.tmdbexplorer.utilities.EndlessRecyclerViewScrollListener
import com.androidcodechallenge.tmdbexplorer.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies_list.*
import javax.inject.Inject


class MoviesListingFragment : BaseFragment(), MovieListingAdapter.OnItemClickedListener,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var movieViewModel: MovieViewModel
    private var layoutManager = LinearLayoutManager(context)
    private var movieListingAdapter: MovieListingAdapter? = null

    override fun inject() {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_movies_list, container, false)

        movieViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MovieViewModel::class.java)
        movieViewModel?.loadMoviesList()?.observe(this, MoviesListObserver)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        swipeToRefreshLayout.setOnRefreshListener(this)
        swipeToRefreshLayout.setProgressViewOffset(false, 0, 200)
        swipeToRefreshLayout.isRefreshing
        searchItem.setOnClickListener({
            context?.let {
                if (searchView.text.trim().isNotEmpty()) {
                    movieListingAdapter!!.clearItems()
                    movieListingAdapter!!.notifyDataSetChanged()
                    movieViewModel?.getMoviesByQuery(searchView.text.toString())?.observe(this, MoviesListObserver)
                }
            }
        })
    }

    override fun openDetailView(movies: Movie) {
        startActivity(MovieDetailActivity.getInstance(context, movies))
    }

    override fun onRefresh() {
        searchView.text.clear()
        movieListingAdapter?.clearItems()
        movieListingAdapter?.notifyDataSetChanged()
        movieListingAdapter?.setIsLoading(false)
        movieViewModel.refreshMovieViewModel()
        movieViewModel.loadMoviesList()?.observe(this, MoviesListObserver)
    }

    private fun setAdapter() {

        movieListRecyclerView.layoutManager = layoutManager
        movieListingAdapter = MovieListingAdapter(this.context!!, this)
        movieListRecyclerView.adapter = movieListingAdapter

        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                movieListingAdapter?.setIsLoading(true)
                movieViewModel?.loadMoviesList()?.observe(this@MoviesListingFragment, MoviesListObserver)
            }
        }
        movieListRecyclerView.addOnScrollListener(scrollListener)
    }

    private var MoviesListObserver = Observer<ApiResponseResource<List<Movie>>> {

        when (it?.status) {
            Status.LOADING -> {
                if (!movieListingAdapter?.isLoading()!!) {
                    showLoading()
                    swipeToRefreshLayout.isRefreshing = false
                }
            }
            Status.ERROR -> {
                if (!movieListingAdapter?.isLoading()!!) {
                    hideLoading()
                    swipeToRefreshLayout.isRefreshing = false

                }

                toast(it.message!!)

                movieListingAdapter?.setIsLoading(false)
            }
            Status.SUCCESS -> {
                swipeToRefreshLayout.isRefreshing = false

                if (!movieListingAdapter?.isLoading()!!) {
                    hideLoading()
                }

                if (it.data!!.isNotEmpty()) {

                    movieListingAdapter?.appendItems(it.data!!)
                }
                movieListingAdapter?.setIsLoading(false)
            }
            Status.NO_MORE_ITEM -> {
                movieListingAdapter?.setIsLoading(false)
            }
        }
    }

}