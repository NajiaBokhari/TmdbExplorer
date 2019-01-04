package com.androidcodechallenge.tmdbexplorer.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.androidcodechallenge.tmdbexplorer.R
import com.androidcodechallenge.tmdbexplorer.models.Movie
import com.androidcodechallenge.tmdbexplorer.repositories.MoviesRepository
import com.androidcodechallenge.tmdbexplorer.rest.ApiResponseResource
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : BaseViewModel() {

    private lateinit var movies: Movie

    var moviesListResponseLiveData: MutableLiveData<ApiResponseResource<List<Movie>>>? = MutableLiveData()
    var queryResponseLiveData: MutableLiveData<ApiResponseResource<List<Movie>>>? = MutableLiveData()

    fun setMovieDetail(movie: Movie) {
        movies = movie
    }

    fun getMovieDetail(): Movie? {
        return movies
    }

    fun refreshMovieViewModel()  {
        currentPage=0
        queryResponseLiveData?.postValue(ApiResponseResource.noMoreItem())
        moviesListResponseLiveData?.postValue(ApiResponseResource.noMoreItem())
    }

    fun setMissingValue(context: Context, field: Int, textValue: Boolean): String {
        var setValue: String = field.toString()
        if (field == 0 && textValue) {
            setValue = context.getString(R.string.null_revenue_txt)
        } else if (field == 0 && !textValue) {
            setValue = context.getString(R.string.null_duration_txt)
        }
        return setValue
    }


    fun loadMoviesList(): MutableLiveData<ApiResponseResource<List<Movie>>>? {
        moviesListResponseLiveData?.postValue(ApiResponseResource.loading())

        if (shouldLoadMore()) {
            compositeDisposable.add(
                moviesRepository.loadMoviesList(currentPage)
                    .subscribe({ apiResponse ->
//                        moviesListResponseLiveData?.value = ApiResponseResource.loading()
                        setItemTotal(apiResponse.totalPage)
                        moviesListResponseLiveData?.value = ApiResponseResource.success(apiResponse.items)
                    }, { onError ->
                        moviesListResponseLiveData?.value = ApiResponseResource.error(onHandleError(onError))
                    })
            )

        } else {
            moviesListResponseLiveData?.postValue(ApiResponseResource.noMoreItem())
        }

        return moviesListResponseLiveData
    }

    fun getMoviesByQuery(status: String): MutableLiveData<ApiResponseResource<List<Movie>>>? {
          queryResponseLiveData?.value = ApiResponseResource.loading()
        compositeDisposable.add(
            moviesRepository.loadMoviesByTitle(status, currentPage)
                .subscribe({ apiResponse ->
                    setItemTotal(apiResponse.totalPage)
                    queryResponseLiveData?.value = ApiResponseResource.success(apiResponse.items)
                }, { onError ->
                    queryResponseLiveData?.value = ApiResponseResource.error(onHandleError(onError))
                })
        )

        return queryResponseLiveData
    }

}