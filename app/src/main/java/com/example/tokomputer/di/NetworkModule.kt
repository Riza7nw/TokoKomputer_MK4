package com.example.tokomputer.di

import com.example.tokomputer.data.remote.api.ApiService
import com.example.tokomputer.data.remote.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    // Ganti sesuai kondisi kamu:
    // Emulator  → http://10.0.2.2:8000/api/
    // Device HP → http://192.168.x.x:8000/api/ (IP laptop kamu)
    // Production → https://domain-kamu.com/api/
    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    // Logging untuk debug di Logcat
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttp client dengan interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())   // token bearer
        .addInterceptor(loggingInterceptor)  // logging
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // ApiService instance — ini yang dipakai di Repository
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}