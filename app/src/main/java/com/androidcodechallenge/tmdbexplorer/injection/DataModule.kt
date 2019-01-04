package com.androidcodechallenge.tmdbexplorer.injection

import com.androidcodechallenge.tmdbexplorer.rest.services.MoviesWebService
import com.androidcodechallenge.tmdbexplorer.storage.PreferenceStorage
import com.androidcodechallenge.tmdbexplorer.storage.TokenStorage
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun getTokenStorage(): TokenStorage {
        return PreferenceStorage()
    }

    @Singleton
    @Provides
    fun getProductsWebService(@Named("publicClient") retrofit: Retrofit): MoviesWebService {
        return retrofit.create(MoviesWebService::class.java)
    }
}