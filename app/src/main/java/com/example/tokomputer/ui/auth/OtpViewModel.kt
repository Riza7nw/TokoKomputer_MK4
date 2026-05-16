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

    private val _otpState = MutableLiveData<Resource<Unit>>()
    val otpState: LiveData<Resource<Unit>> = _otpState

    private val _resendState = MutableLiveData<Resource<Unit>>()
    val resendState: LiveData<Resource<Unit>> = _resendState

    // Timer countdown 5 menit = 300 detik
    private val _timerSeconds = MutableLiveData(300)
    val timerSeconds: LiveData<Int> = _timerSeconds

    private val _isTimerRunning = MutableLiveData(false)
    val isTimerRunning: LiveData<Boolean> = _isTimerRunning

    private var timerJob: Job? = null

    // ===== VERIFY OTP =====
    fun verifyOtp(email: String, otp: String) {

        if (otp.isBlank()) {
            _otpState.value = Resource.Error("Masukkan kode OTP")
            return
        }

        if (otp.length < 6) {
            _otpState.value = Resource.Error("OTP harus 6 digit")
            return
        }

        _otpState.value = Resource.Loading()

        viewModelScope.launch {
            val result = repository.verifyOtp(email, otp)
            _otpState.value = result
        }
    }

    // ===== RESEND OTP =====
    fun resendOtp(email: String) {
        _resendState.value = Resource.Loading()

        viewModelScope.launch {
            // Register ulang dengan email yang sama tidak cocok
            // Kita perlu endpoint resend OTP di Laravel
            // Sementara pakai verifyOtp sebagai placeholder
            // Nanti akan diupdate setelah endpoint resend dibuat
            delay(1000)
            _resendState.value = Resource.Success(Unit)
            startTimer()
        }
    }

    // ===== TIMER =====
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

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}