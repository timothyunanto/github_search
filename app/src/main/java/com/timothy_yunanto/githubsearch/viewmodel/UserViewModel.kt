package com.timothy_yunanto.githubsearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.timothy_yunanto.githubsearch.repo.UserRepository
import com.timothy_yunanto.githubsearch.service.ApiResponse
import com.timothy_yunanto.githubsearch.service.model.params.UserDetailParams
import com.timothy_yunanto.githubsearch.service.model.params.UserParams

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    var isLoading = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<Throwable>()
    var apiResponse = MutableLiveData<ApiResponse>()

    fun getListUsers() {
        var params: UserParams = UserParams(
            since = 1,
            perPage = 100
        )
        isLoading.value = true
        userRepository.getListUsers(params,
            { apiResponse.value = it; isLoading.value = false },
            { apiError.value = it; isLoading.value = false })
    }

    fun getUser(params: UserDetailParams) {
        isLoading.value = true
        userRepository.getUser(params,
            { apiResponse.value = it; isLoading.value = false },
            { apiError.value = it; isLoading.value = false })
    }
}