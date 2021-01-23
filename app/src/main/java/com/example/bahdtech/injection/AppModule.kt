package com.example.bahdtech.injection

import com.example.bahdtech.api.GithubService
import com.example.bahdtech.ui.main.MainViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author ADIO Kingsley O.
 * @since 14 Jul, 2019
 */
object AppModule {

    private val appModule = module {
        single<Retrofit> {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        single<GithubService> {
            get<Retrofit>().create(GithubService::class.java)
        }
    }

    private val uiModule = module {
        viewModel {
            MainViewModel(get())
        }
    }


    val modules: List<Module> = listOf(appModule, uiModule)
}
