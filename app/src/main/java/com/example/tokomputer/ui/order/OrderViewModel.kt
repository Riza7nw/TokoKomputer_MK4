package com.example.tokomputer.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokomputer.data.remote.dto.request.TransactionItemRequest
import com.example.tokomputer.data.remote.dto.request.TransactionRequest
import com.example.tokomputer.data.repository.TransactionRepository
import com.example.tokomputer.di.NetworkModule
import com.example.tokomputer.model.CartItem
import com.example.tokomputer.model.TransactionModel
import com.example.tokomputer.utils.Resource
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private val repository = TransactionRepository(NetworkModule.apiService)

    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _totalPrice = MutableLiveData(0.0)
    val totalPrice: LiveData<Double> = _totalPrice

    private val _checkoutState = MutableLiveData<Resource<TransactionModel>>()
    val checkoutState: LiveData<Resource<TransactionModel>> = _checkoutState

    // ===== CART MANAGEMENT =====

    fun addToCart(item: CartItem) {
        val current = _cartItems.value?.toMutableList() ?: mutableListOf()
        val existing = current.find { it.productId == item.productId }
        if (existing != null) {
            existing.quantity++
        } else {
            current.add(item)
        }
        _cartItems.value = current
        recalculateTotal()
    }

    fun increaseQuantity(productId: Int) {
        val current = _cartItems.value?.toMutableList() ?: return
        current.find { it.productId == productId }?.let { it.quantity++ } // ← fix
        _cartItems.value = current
        recalculateTotal()
    }

    fun decreaseQuantity(productId: Int) {
        val current = _cartItems.value?.toMutableList() ?: return
        val item = current.find { it.productId == productId } ?: return
        if (item.quantity > 1) {
            item.quantity--
        } else {
            current.remove(item)
        }
        _cartItems.value = current
        recalculateTotal()
    }

    fun removeItem(productId: Int) {
        val current = _cartItems.value?.toMutableList() ?: return
        current.removeAll { it.productId == productId }
        _cartItems.value = current
        recalculateTotal()
    }

    private fun recalculateTotal() {
        _totalPrice.value = _cartItems.value?.sumOf { it.subtotal } ?: 0.0
    }

    fun isCartEmpty(): Boolean = _cartItems.value.isNullOrEmpty()

    // ===== CHECKOUT =====

    fun checkout() {
        val items = _cartItems.value
        if (items.isNullOrEmpty()) {
            _checkoutState.value = Resource.Error("Keranjang kosong")
            return
        }

        _checkoutState.value = Resource.Loading()

        viewModelScope.launch {
            val request = TransactionRequest(
                items = items.map {
                    TransactionItemRequest(
                        product_id = it.productId,
                        quantity   = it.quantity
                    )
                }
            )
            val result = repository.createTransaction(request)
            _checkoutState.value = result
        }
    }
}