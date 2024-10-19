package com.timothy_yunanto.githubsearch.service.model.params

import com.google.gson.annotations.SerializedName

data class UserDetailParams (
    @SerializedName("username")
    val username: String
)