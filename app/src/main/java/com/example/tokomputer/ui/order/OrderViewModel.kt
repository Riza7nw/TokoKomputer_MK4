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

    private val _cartItems = MutableLiveData<List<CartItem>>(staticCart)
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _totalPrice = MutableLiveData(calculateTotal())
    val totalPrice: LiveData<Double> = _totalPrice

    private val _checkoutState = MutableLiveData<Resource<TransactionModel>>()
    val checkoutState: LiveData<Resource<TransactionModel>> = _checkoutState

    init {
        // Sync dari static cart saat ViewModel dibuat
        _cartItems.value = staticCart.toList()
        recalculateTotal()
    }

    // ===== STATIC CART — persist selama app hidup =====
    companion object {
        private val staticCart = mutableListOf<CartItem>()

        fun addToStaticCart(item: CartItem) {
            val existing = staticCart.find { it.productId == item.productId }
            if (existing != null) {
                existing.quantity++
            } else {
                staticCart.add(item)
            }
        }

        fun clearStaticCart() {
            staticCart.clear()
        }

        private fun calculateTotal(): Double {
            return staticCart.sumOf { it.subtotal }
        }
    }

    // ===== CART MANAGEMENT =====

    fun addToCart(item: CartItem) {
        addToStaticCart(item)
        _cartItems.value = staticCart.toList()
        recalculateTotal()
    }

    fun increaseQuantity(productId: Int) {
        staticCart.find { it.productId == productId }?.let { it.quantity++ }
        _cartItems.value = staticCart.toList()
        recalculateTotal()
    }

    fun decreaseQuantity(productId: Int) {
        val item = staticCart.find { it.productId == productId } ?: return
        if (item.quantity > 1) {
            item.quantity--
        } else {
            staticCart.remove(item)
        }
        _cartItems.value = staticCart.toList()
        recalculateTotal()
    }

    fun removeItem(productId: Int) {
        staticCart.removeAll { it.productId == productId }
        _cartItems.value = staticCart.toList()
        recalculateTotal()
    }

    private fun recalculateTotal() {
        _totalPrice.value = staticCart.sumOf { it.subtotal }
    }

    fun isCartEmpty(): Boolean = staticCart.isEmpty()

    // ===== CHECKOUT =====

    fun checkout() {
        if (staticCart.isEmpty()) {
            _checkoutState.value = Resource.Error("Keranjang kosong")
            return
        }

        _checkoutState.value = Resource.Loading()

        viewModelScope.launch {
            val request = TransactionRequest(
                items = staticCart.map {
                    TransactionItemRequest(
                        product_id = it.productId,
                        quantity   = it.quantity
                    )
                }
            )
            val result = repository.createTransaction(request)
            if (result is Resource.Success) {
                clearStaticCart() // Bersihkan cart setelah berhasil
            }
            _checkoutState.value = result
        }
    }
}