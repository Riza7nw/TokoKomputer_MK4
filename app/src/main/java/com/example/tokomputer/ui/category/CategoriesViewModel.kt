package com.example.tokomputer.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.repository.ProductRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val repository = ProductRepository(NetworkModule.apiService)

    private val _categoriesState = MutableLiveData<Resource<List<String>>>()
    val categoriesState: LiveData<Resource<List<String>>> = _categoriesState

    init {
        fetchCategories()
    }

    fun fetchCategories() {
        _categoriesState.value = Resource.Loading()
        viewModelScope.launch {
            _categoriesState.value = repository.getCategories()
        }
    }
}