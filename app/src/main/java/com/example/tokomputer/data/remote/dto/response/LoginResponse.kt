package com.example.tokomputer.data.remote.dto.response

data class LoginResponse(
    val token: String,
    val user: UserResponse
)

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String?
)