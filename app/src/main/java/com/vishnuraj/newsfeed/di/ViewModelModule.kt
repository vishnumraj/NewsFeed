package com.vishnuraj.newsfeed.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vishnuraj.newsfeed.base.di.ViewModelFactory
import com.vishnuraj.newsfeed.base.di.ViewModelKey
import com.vishnuraj.newsfeed.presentation.viewmodels.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    internal abstract fun newsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    //Add more ViewModels here
}