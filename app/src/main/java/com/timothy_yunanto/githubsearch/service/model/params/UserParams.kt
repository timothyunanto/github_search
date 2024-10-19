package com.timothy_yunanto.githubsearch.service.model.params

import com.google.gson.annotations.SerializedName

data class UserParams (
    @SerializedName("since")
    var since: Int,
    @SerializedName("per_page")
    val perPage: Int? = 30
)