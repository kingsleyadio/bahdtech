package com.example.bahdtech.api

import com.squareup.moshi.Json

/**
 * @author ADIO Kingsley O.
 * @since 14 Jul, 2019
 */
class GithubUser(
    @field:Json(name = "login")
    val username: String,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "avatar_url")
    val avatarUrl: String,
    @field:Json(name = "repos_url")
    val reposUrl: String
)
