package com.androidcodechallenge.tmdbexplorer.repositories

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

abstract class BaseRepository {

    private val schedulersTransformer: SingleTransformer<Objects, Objects> = SingleTransformer { observable ->

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> applySchedulers(): SingleTransformer<T, T> {
        return schedulersTransformer as SingleTransformer<T, T>
    }
}