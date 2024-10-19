package com.timothy_yunanto.githubsearch.repo

import android.app.Application
import com.timothy_yunanto.githubsearch.service.ApiClient
import com.timothy_yunanto.githubsearch.service.ApiResponse
import com.timothy_yunanto.githubsearch.service.model.params.UserDetailParams
import com.timothy_yunanto.githubsearch.service.model.params.UserParams
import retrofit2.Call
import retrofit2.Response

class UserRepository(application: Application) {
    private val app = application

    fun getListUsers(
        params: UserParams,
        successHandler: (ApiResponse) -> Unit,
        failureHandler: (Throwable) -> Unit
    ) {
        ApiClient.provideApiService().listUsers(params.since, params.perPage!!)
            .enqueue(object : retrofit2.Callback<ApiResponse> {
                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                    failureHandler(t)
                }

                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>
                ) {
                    response.body()?.let { successHandler(it) }
                }
            })
    }

    fun getUser(
        params: UserDetailParams,
        successHandler: (ApiResponse) -> Unit,
        failureHandler: (Throwable) -> Unit
    ) {
        ApiClient.provideApiService().getUser(params.username)
            .enqueue(object : retrofit2.Callback<ApiResponse> {
                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                    failureHandler(t)
                }

                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>
                ) {
                    response.body()?.let { successHandler(it) }
                }
            })
    }
}