package com.example.bahdtech.injection

import com.example.bahdtech.api.GithubService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

/**
 * @author ADIO Kingsley O.
 * @since 14 Jul, 2019
 */
@Module
object AppModule {

    @Single
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Single
    fun provideGithubService(retrofit: Retrofit): GithubService {
        return retrofit.create<GithubService>()
    }
}
