package com.example.bahdtech.api

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author ADIO Kingsley O.
 * @since 14 Jul, 2019
 */
interface GithubService {

    @GET("users")
    suspend fun listUsers(@Query("since") cursor: Int): List<GithubUser>
}
