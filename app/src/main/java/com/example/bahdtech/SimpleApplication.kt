package com.example.bahdtech

import android.app.Application
import com.example.bahdtech.injection.AppGraphProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory

/**
 * @author ADIO Kingsley O.
 * @since 14 Jul, 2019
 */
class SimpleApplication : Application(), AppGraphProvider {

    override val appGraph = createGraphFactory<AppGraph.Factory>().create(this)
}


@DependencyGraph(AppScope::class, isExtendable = true)
interface AppGraph {
    @DependencyGraph.Factory
    interface Factory {
        fun create(@Provides app: Application): AppGraph
    }
}
