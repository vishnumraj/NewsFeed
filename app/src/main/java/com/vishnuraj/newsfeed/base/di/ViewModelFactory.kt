package com.vishnuraj.newsfeed.base.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Android in house ViewModels cannot be injected via dagger. So custom ViewModelFactory has to
 * be created where theMap representing the ViewModel class as Key and the Dagger ViewModel provider
 * can be injected.
 *
 * @param viewModels The Map representing the ViewModel class the DaggerProvider of the ViewModel
 */
@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    /**
     * function to retrieve the ViewModel based on the ViewModel class. The function looks for the ViewModel
     * with the Map created by the dagger using the viewModel class as Key.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModels[modelClass]?.get() as T
}

/**
 * ViewModelKey annotation, when used on provider methods, basically says that the services returned by these
 * methods must be put into special Map. The keys in this Map will be of type ViewModel class and the
 * values will be of type instance of ViewModel class
 *
 * @param value The actual subclass extending the ViewModel.
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
