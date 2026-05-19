package com.example.tokomputer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.repository.ProductRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.model.ProductModel
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository(NetworkModule.apiService)

    private val _productsState = MutableLiveData<Resource<List<ProductModel>>>()
    val productsState: LiveData<Resource<List<ProductModel>>> = _productsState

    // Simpan semua produk untuk filter search
    private var allProducts: List<ProductModel> = emptyList()

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        _productsState.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.getProducts()
            if (result is Resource.Success) {
                allProducts = result.data ?: emptyList()
            }
            _productsState.value = result
        }
    }

    // Filter produk berdasarkan keyword search
    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _productsState.value = Resource.Success(allProducts)
            return
        }
        val filtered = allProducts.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.category?.contains(query, ignoreCase = true) == true
        }
        _productsState.value = Resource.Success(filtered)
    }
}