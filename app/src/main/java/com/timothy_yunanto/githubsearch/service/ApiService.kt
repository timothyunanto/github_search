package com.timothy_yunanto.githubsearch.service

import com.timothy_yunanto.githubsearch.service.model.params.UserDetailParams
import com.timothy_yunanto.githubsearch.service.model.params.UserParams
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Query
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun listUsers(@Query("since") since: Int, @Query("per_page") perPage: Int): Call<ApiResponse>
    @GET("users/")
    fun getUser(@Query("username") username: String): Call<ApiResponse>
}