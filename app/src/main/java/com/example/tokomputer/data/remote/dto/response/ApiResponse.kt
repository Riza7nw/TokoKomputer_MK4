package com.example.tokomputer.data.remote.dto.response

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)