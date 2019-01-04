package com.androidcodechallenge.tmdbexplorer.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidcodechallenge.tmdbexplorer.viewmodels.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun movieViewModel(viewModel: MovieViewModel): ViewModel

}