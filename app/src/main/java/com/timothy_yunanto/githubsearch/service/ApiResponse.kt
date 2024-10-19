package com.timothy_yunanto.githubsearch.service

import com.timothy_yunanto.githubsearch.service.model.response.User

data class ApiResponse (
    val data: List<User>
)