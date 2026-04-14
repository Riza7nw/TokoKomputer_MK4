package com.example.tokomputer.model

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val imageRes: Int, // drawable resource id (or use String for URL)
    val desc: String? = null,
    val specsResId: Int? = null,
    val specsText: String? = null
)