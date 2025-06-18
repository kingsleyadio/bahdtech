package com.example.bahdtech.injection

import android.app.Application
import com.example.bahdtech.AppGraph
import com.example.bahdtech.api.GithubService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

/**
 * @author ADIO Kingsley O.
 * @since 14 Jul, 2019
 */
@ContributesTo(AppScope::class)
interface AppModule {

    @Provides
    @SingleIn(AppScope::class)
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @SingleIn(AppScope::class)
    fun provideGithubService(retrofit: Retrofit): GithubService {
        return retrofit.create<GithubService>()
    }
}


interface AppGraphProvider {
    val appGraph: AppGraph
}

object Injection {
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> fromApplication(app: Application): T {
        val appGraph = (app as AppGraphProvider).appGraph
        return appGraph as T
    }
}
