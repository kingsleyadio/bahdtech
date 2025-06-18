package com.example.bahdtech.injection

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.bahdtech.AppGraph
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Extends
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory
import kotlin.reflect.KClass

class ViewModelScope private constructor()

@DependencyGraph(ViewModelScope::class)
interface ViewModelGraph {
    val viewModelMap: Map<KClass<*>, Provider<ViewModel>>

    @Multibinds(allowEmpty = true)
    fun provideViewModels(): Map<KClass<*>, ViewModel>

    @Provides
    fun provideSavedStateHandle(extras: CreationExtras): SavedStateHandle {
        return extras.createSavedStateHandle()
    }

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Extends appGraph: AppGraph,
            @Provides extras: CreationExtras
        ): ViewModelGraph
    }
}

@ContributesTo(AppScope::class)
interface ViewModelProviderFactoryProvider {
    val viewModelProviderFactory: ViewModelProvider.Factory
}

@ContributesBinding(AppScope::class)
class MultibindingViewModelFactory @Inject constructor(
    private val appGraph: AppGraph,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val viewModelGraph = createGraphFactory<ViewModelGraph.Factory>().create(appGraph, extras)
        val viewModelMap = viewModelGraph.viewModelMap
        Log.d("King", viewModelMap.toString())

        val viewModelProvider = viewModelMap.getOrElse(modelClass.kotlin) {
            error(
                "${modelClass.name} could not be instantiated. Did you forget to contribute it? Ensure the " +
                        "view model class is configured with the necessary DI  annotations"
            )
        }
        return viewModelProvider() as T
    }
}

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.injectViewModel(
    noinline extrasProducer: (() -> CreationExtras)? = null,
): Lazy<VM> = viewModels(extrasProducer) {
    val appGraph = Injection.fromApplication<ViewModelProviderFactoryProvider>(application)
    appGraph.viewModelProviderFactory
}

@MainThread
inline fun <reified VM : ViewModel> Fragment.injectViewModel(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline extrasProducer: (() -> CreationExtras)? = null,
): Lazy<VM> = viewModels(ownerProducer, extrasProducer) {
    val app = requireActivity().application
    val appGraph = Injection.fromApplication<ViewModelProviderFactoryProvider>(app)
    appGraph.viewModelProviderFactory
}
