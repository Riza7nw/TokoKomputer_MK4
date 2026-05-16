package com.example.tokomputer.data.repository

import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.data.remote.api.ApiService
import com.example.tokomputer.data.remote.api.LoginData
import com.example.tokomputer.data.remote.dto.request.LoginRequest
import com.example.tokomputer.data.remote.dto.request.RegisterRequest
import com.example.tokomputer.model.UserModel
import com.example.tokomputer.utils.Resource

class AuthRepository(private val apiService: ApiService) {

    //  REGISTER
    suspend fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        phone: String
    ): Resource<Unit> {
        return try {
            val response = apiService.register(
                RegisterRequest(name, email, password, passwordConfirmation, phone)
            )

            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(Unit)
            } else {
                val errorMsg = response.body()?.message
                    ?: response.errorBody()?.string()
                    ?: "Register gagal"
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server: ${e.message}")
        }
    }

    //  VERIFY OTP
    suspend fun verifyOtp(email: String, otp: String): Resource<Unit> {
        return try {
            val response = apiService.verifyOtp(
                mapOf("email" to email, "otp" to otp)
            )

            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(Unit)
            } else {
                val errorMsg = response.body()?.message
                    ?: "Verifikasi OTP gagal"
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server: ${e.message}")
        }
    }

    //  LOGIN
    suspend fun login(email: String, password: String): Resource<LoginData> {
        return try {
            val response = apiService.login(
                LoginRequest(email, password)
            )

            if (response.isSuccessful && response.body()?.success == true) {
                val loginData = response.body()?.data

                if (loginData != null) {
                    // Simpan token & data user ke SessionManager
                    SessionManager.saveToken(loginData.token)
                    SessionManager.saveUserName(loginData.user.name)
                    SessionManager.saveUserEmail(loginData.user.email)
                    Resource.Success(loginData)
                } else {
                    Resource.Error("Data login kosong")
                }
            } else {
                val errorMsg = response.body()?.message
                    ?: "Email atau password salah"
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server: ${e.message}")
        }
    }

    //  LOGOUT
    suspend fun logout(): Resource<Unit> {
        return try {
            val response = apiService.logout()

            // Hapus session lokal apapun hasilnya
            SessionManager.clearLogin()

            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Logout gagal")
            }
        } catch (e: Exception) {
            // Tetap clear local session meski server error
            SessionManager.clearLogin()
            Resource.Success(Unit)
        }
    }

    //  GET PROFILE
    suspend fun getMe(): Resource<UserModel> {
        return try {
            val response = apiService.getMe()

            if (response.isSuccessful && response.body()?.success == true) {
                val user = response.body()?.data
                if (user != null) {
                    Resource.Success(user)
                } else {
                    Resource.Error("Data user kosong")
                }
            } else {
                Resource.Error("Gagal mengambil data user")
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server: ${e.message}")
        }
    }
}