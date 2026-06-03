package com.example.tokomputer.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.repository.TransactionRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.model.TransactionModel
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.launch

class TransactionHistoryViewModel : ViewModel() {

    private val repository = TransactionRepository(NetworkModule.apiService)

    private val _transactionsState = MutableLiveData<Resource<List<TransactionModel>>>()
    val transactionsState: LiveData<Resource<List<TransactionModel>>> = _transactionsState

    init {
        fetchTransactions()
    }

    fun fetchTransactions() {
        _transactionsState.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.getTransactions()
            _transactionsState.value = result
        }
    }
}