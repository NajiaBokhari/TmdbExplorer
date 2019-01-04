package com.androidcodechallenge.tmdbexplorer.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*

abstract class BaseViewModel : ViewModel() {

    private val schedulersTransformer: SingleTransformer<Objects, Objects> = SingleTransformer { observable ->

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> applySchedulers(): SingleTransformer<T, T> {
        return schedulersTransformer as SingleTransformer<T, T>
    }

    protected var currentPage: Int = 0

    private var totalCount: Int = 1

    protected var pageSize = 10

    val compositeDisposable = CompositeDisposable()

    fun setItemTotal(total: Int) {
        totalCount = total
    }

    fun resetItemCount() {
        currentPage = 0
        totalCount = 1
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun onHandleError(error: Throwable): String {

        when (error) {
            is HttpException -> when (error.code()) {
                401 -> {
                    return "Your session has expired please sign in again!"
                }
            }
            is SocketTimeoutException -> return "Problem to connect server!"
            is IOException -> return "Please connect to Internet!"
        }

        return error.message ?: "unknown error"
    }

    fun shouldLoadMore(): Boolean {

        return if (currentPage * pageSize < totalCount) {
            currentPage++
            true
        } else {
            false
        }

    }
}