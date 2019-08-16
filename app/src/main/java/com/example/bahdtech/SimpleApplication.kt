package com.example.bahdtech

import android.app.Application
import com.example.bahdtech.injection.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @author ADIO Kingsley O.
 * @since 14 Jul, 2019
 */
class SimpleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SimpleApplication)
            modules(AppModule.modules)
        }
    }
}
