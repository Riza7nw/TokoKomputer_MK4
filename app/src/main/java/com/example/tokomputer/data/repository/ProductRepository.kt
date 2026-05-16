package com.example.tokomputer.data.repository

import com.example.tokomputer.data.remote.api.ApiService
import com.example.tokomputer.model.ProductModel
import com.example.tokomputer.utils.Resource

class ProductRepository(private val apiService: ApiService) {

    // GET ALL PRODUCTS
    suspend fun getProducts(): Resource<List<ProductModel>> {
        return try {
            val response = apiService.getProducts()

            if (response.isSuccessful && response.body()?.success == true) {
                val products = response.body()?.data
                if (products != null) {
                    Resource.Success(products)
                } else {
                    Resource.Error("Data produk kosong")
                }
            } else {
                val errorMsg = response.body()?.message
                    ?: "Gagal mengambil produk"
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server: ${e.message}")
        }
    }

    // GET PRODUCT DETAIL
    suspend fun getProductById(id: Int): Resource<ProductModel> {
        return try {
            val response = apiService.getProductById(id)

            if (response.isSuccessful && response.body()?.success == true) {
                val product = response.body()?.data
                if (product != null) {
                    Resource.Success(product)
                } else {
                    Resource.Error("Data produk kosong")
                }
            } else {
                val errorMsg = response.body()?.message
                    ?: "Produk tidak ditemukan"
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server: ${e.message}")
        }
    }
}