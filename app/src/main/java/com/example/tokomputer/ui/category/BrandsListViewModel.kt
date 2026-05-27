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

class BrandListViewModel : ViewModel() {

    private val repository = ProductRepository(NetworkModule.apiService)

    private val _productsState = MutableLiveData<Resource<List<ProductModel>>>()
    val productsState: LiveData<Resource<List<ProductModel>>> = _productsState

    private var allProducts: List<ProductModel> = emptyList()

    fun fetchByBrand(brand: String) {
        _productsState.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.getProducts()
            if (result is Resource.Success) {
                val filtered = result.data?.filter {
                    it.brand?.equals(brand, ignoreCase = true) == true
                } ?: emptyList()
                allProducts = filtered
                _productsState.value = Resource.Success(filtered)
            } else {
                _productsState.value = result as Resource<List<ProductModel>>
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