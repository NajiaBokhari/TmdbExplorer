package com.androidcodechallenge.tmdbexplorer.rest

import androidx.annotation.Nullable
import com.androidcodechallenge.tmdbexplorer.rest.Status.ERROR
import com.androidcodechallenge.tmdbexplorer.rest.Status.LOADING
import com.androidcodechallenge.tmdbexplorer.rest.Status.NO_MORE_ITEM
import com.androidcodechallenge.tmdbexplorer.rest.Status.SUCCESS


class ApiResponseResource<T> private constructor(val status: Int, @param:Nullable @field:Nullable val message: String?) {

    var data: T? = null

    constructor(status: Int, response: T, message: String?) : this(status, message){
        data = response
    }

    companion object {

        fun <T> success(data: T): ApiResponseResource<T> {
            return ApiResponseResource(SUCCESS, data, "success")
        }

        fun <T> error(msg: String): ApiResponseResource<T> {
            return ApiResponseResource(ERROR, msg)
        }

        fun <T> loading(@Nullable msg: String): ApiResponseResource<T> {
            return ApiResponseResource(LOADING, msg)
        }

        fun <T> loading(): ApiResponseResource<T> {
            return ApiResponseResource(LOADING, "loading")
        }

        fun <T> noMoreItem(): ApiResponseResource<T> {
            return ApiResponseResource(NO_MORE_ITEM, "no more items exist")
        }
    }
}