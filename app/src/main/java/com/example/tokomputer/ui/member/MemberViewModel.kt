package com.example.tokomputer.ui.member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.repository.AuthRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.model.UserModel
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.launch

class MemberViewModel : ViewModel() {

    private val repository = AuthRepository(NetworkModule.apiService)

    private val _profileState = MutableLiveData<Resource<UserModel>>()
    val profileState: LiveData<Resource<UserModel>> = _profileState

    private val _logoutState = MutableLiveData<Resource<Unit>>()
    val logoutState: LiveData<Resource<Unit>> = _logoutState

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        _profileState.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.getMe()
            _profileState.value = result
        }
    }

    fun logout() {
        _logoutState.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.logout()
            _logoutState.value = result
        }
    }
}