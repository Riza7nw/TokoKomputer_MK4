package com.example.tokomputer.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.repository.AuthRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val repository = AuthRepository(NetworkModule.apiService)

    private val _registerState = MutableLiveData<Resource<Unit>>()
    val registerState: LiveData<Resource<Unit>> = _registerState

    fun register(name: String, email: String, password: String, confirmPassword: String, phone: String) {

        // Validasi input
        when {
            name.isBlank() || email.isBlank() || password.isBlank() -> {
                _registerState.value = Resource.Error("Semua field harus diisi")
                return
            }
            password != confirmPassword -> {
                _registerState.value = Resource.Error("Password tidak cocok")
                return
            }
            password.length < 6 -> {
                _registerState.value = Resource.Error("Password minimal 6 karakter")
                return
            }
        }

        _registerState.value = Resource.Loading()

        viewModelScope.launch {
            val result = repository.register(name, email, password, confirmPassword, phone)
            _registerState.value = result
        }
    }
}