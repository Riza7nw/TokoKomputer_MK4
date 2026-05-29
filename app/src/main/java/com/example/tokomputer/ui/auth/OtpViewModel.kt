package com.example.tokomputer.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.repository.AuthRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpViewModel : ViewModel() {

    private val repository = AuthRepository(NetworkModule.apiService)

    private val _verifyState = MutableLiveData<Resource<Unit>>()
    val verifyState: LiveData<Resource<Unit>> = _verifyState

    private val _resendState = MutableLiveData<Resource<Unit>>()
    val resendState: LiveData<Resource<Unit>> = _resendState

    // Timer countdown
    private val _timerSeconds = MutableLiveData(300) // 5 menit = 300 detik
    val timerSeconds: LiveData<Int> = _timerSeconds

    private val _isTimerRunning = MutableLiveData(true)
    val isTimerRunning: LiveData<Boolean> = _isTimerRunning

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    fun startTimer() {
        timerJob?.cancel()
        _timerSeconds.value = 300
        _isTimerRunning.value = true

        timerJob = viewModelScope.launch {
            var seconds = 300
            while (seconds > 0) {
                delay(1000)
                seconds--
                _timerSeconds.value = seconds
            }
            _isTimerRunning.value = false
        }
    }

    fun verifyOtp(email: String, otp: String) {
        if (otp.length != 6) {
            _verifyState.value = Resource.Error("OTP harus 6 digit")
            return
        }
        _verifyState.value = Resource.Loading()
        viewModelScope.launch {
            _verifyState.value = repository.verifyOtp(email, otp)
        }
    }

    fun resendOtp(email: String) {
        _resendState.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.resendOtp(email)
            _resendState.value = result
            if (result is Resource.Success) {
                startTimer() // Reset timer setelah OTP berhasil dikirim ulang
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}