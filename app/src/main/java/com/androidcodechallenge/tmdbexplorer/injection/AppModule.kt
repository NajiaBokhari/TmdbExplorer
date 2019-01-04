package com.androidcodechallenge.tmdbexplorer.injection

import android.content.Context
import com.androidcodechallenge.tmdbexplorer.AndroidApp
import com.androidcodechallenge.tmdbexplorer.AppConfig
import com.androidcodechallenge.tmdbexplorer.utilities.ConstantUtils.API_URL
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: AndroidApp) {

    @Provides
    @Singleton
    fun provideApp() = app


    @Provides
    @Singleton
    fun provideStoreConfig(): AppConfig {
        return AppConfig(API_URL, "TmdbExplorer")
    }
    
    @Provides
    @AppContext
    internal fun provideContext(): Context {
        return app.applicationContext
    }
}