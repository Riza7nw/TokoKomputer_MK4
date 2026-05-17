package com.example.tokomputer.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.repository.ProductRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.model.ProductModel
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.launch

class CategoryListViewModel : ViewModel() {

    private val repository = ProductRepository(NetworkModule.apiService)

    private val _productsState = MutableLiveData<Resource<List<ProductModel>>>()
    val productsState: LiveData<Resource<List<ProductModel>>> = _productsState

    private var allProducts: List<ProductModel> = emptyList()

    fun fetchByCategory(category: String) {
        _productsState.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.getProducts()
            if (result is Resource.Success) {
                // Filter lokal berdasarkan category
                val filtered = result.data?.filter {
                    it.category?.equals(category, ignoreCase = true) == true
                } ?: emptyList()
                allProducts = filtered
                _productsState.value = Resource.Success(filtered)
            } else {
                _productsState.value = result
            }
        }
    }

    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _productsState.value = Resource.Success(allProducts)
            return
        }
        val filtered = allProducts.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _productsState.value = Resource.Success(filtered)
    }
}