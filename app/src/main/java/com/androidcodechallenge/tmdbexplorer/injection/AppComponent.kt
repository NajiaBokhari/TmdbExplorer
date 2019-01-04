package com.androidcodechallenge.tmdbexplorer.injection

import com.androidcodechallenge.tmdbexplorer.AndroidApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class,RetrofitModule::class,DataModule::class, ViewModelModule::class))
interface AppComponent {

    fun inject(app: AndroidApp)

    fun plus(viewModule: ViewModule):ViewComponent
}