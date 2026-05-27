package com.example.tokomputer.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.repository.ProductRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.launch

class BrandsViewModel : ViewModel() {

    private val repository = ProductRepository(NetworkModule.apiService)

    private val _brandsState = MutableLiveData<Resource<List<String>>>()
    val brandsState: LiveData<Resource<List<String>>> = _brandsState

    init {
        fetchBrands()
    }

    fun fetchBrands() {
        _brandsState.value = Resource.Loading()
        viewModelScope.launch {
            _brandsState.value = repository.getBrands()
        }
    }
}