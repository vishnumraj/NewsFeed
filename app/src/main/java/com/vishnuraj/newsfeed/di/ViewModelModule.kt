package com.vishnuraj.newsfeed.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vishnuraj.newsfeed.base.di.ViewModelFactory
import com.vishnuraj.newsfeed.base.di.ViewModelKey
import com.vishnuraj.newsfeed.presentation.viewmodels.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module class for Injecting the ViewModel into dagger. The ViewModels are injected
 * into Map representing the ViewModel class Name as Key and the actual ViewModel as the
 * value
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    /**
     * Method injects the NewsFeedViewModel into a Map with Key as NewsFeedViewModel::class. The custom
     * ViewModelProvider Factory will make use of this map to retrieve the ViewModel object.
     */
    @Binds
    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    internal abstract fun newsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    //Add more ViewModels here
}