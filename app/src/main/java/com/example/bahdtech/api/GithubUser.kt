package com.example.bahdtech.api

import com.squareup.moshi.Json

/**
 * @author ADIO Kingsley O.
 * @since 14 Jul, 2019
 */
class GithubUser(
    @Json(name = "login")
    val username: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "repos_url")
    val reposUrl: String
)
