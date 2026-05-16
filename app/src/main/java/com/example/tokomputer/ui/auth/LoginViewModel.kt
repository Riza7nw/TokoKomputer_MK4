package com.example.tokomputer.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.remote.api.LoginData
import com.example.tokomputer.data.repository.AuthRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository(NetworkModule.apiService)

    private val _loginState = MutableLiveData<Resource<LoginData>>()
    val loginState: LiveData<Resource<LoginData>> = _loginState

    fun login(email: String, password: String) {

        // Validasi input kosong
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = Resource.Error("Email dan password tidak boleh kosong")
            return
        }

        _loginState.value = Resource.Loading()

        viewModelScope.launch {
            val result = repository.login(email, password)
            _loginState.value = result
        }
    }
}