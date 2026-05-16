package com.example.tokomputer.data.remote.api

import com.example.tokomputer.data.remote.dto.request.LoginRequest
import com.example.tokomputer.data.remote.dto.request.RegisterRequest
import com.example.tokomputer.data.remote.dto.request.TransactionRequest
import com.example.tokomputer.data.remote.dto.response.ApiResponse
import com.example.tokomputer.model.ProductModel
import com.example.tokomputer.model.TransactionModel
import com.example.tokomputer.model.UserModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // AUTH
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<ApiResponse<Unit>>

    @POST("verify-otp")
    suspend fun verifyOtp(
        @Body body: Map<String, String>
    ): Response<ApiResponse<Unit>>

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginData>>

    @POST("logout")
    suspend fun logout(): Response<ApiResponse<Unit>>

    // USER
    @GET("me")
    suspend fun getMe(): Response<ApiResponse<UserModel>>

    // PRODUCTS
    @GET("products")
    suspend fun getProducts(): Response<ApiResponse<List<ProductModel>>>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Response<ApiResponse<ProductModel>>

    // TRANSACTIONS
    @GET("transactions")
    suspend fun getTransactions(): Response<ApiResponse<List<TransactionModel>>>

    @GET("transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") id: Int
    ): Response<ApiResponse<TransactionModel>>

    @POST("transactions")
    suspend fun createTransaction(
        @Body request: TransactionRequest
    ): Response<ApiResponse<TransactionModel>>
}

// Data class khusus untuk response login
data class LoginData(
    val token: String,
    val user: UserModel
)