package com.example.tokomputer.data.repository

import com.example.tokomputer.data.remote.api.ApiService
import com.example.tokomputer.data.remote.dto.request.TransactionRequest
import com.example.tokomputer.model.TransactionModel
import com.example.tokomputer.utils.Resource

class TransactionRepository(private val apiService: ApiService) {

    // GET ALL TRANSACTIONS
    suspend fun getTransactions(): Resource<List<TransactionModel>> {
        return try {
            val response = apiService.getTransactions()

            if (response.isSuccessful && response.body()?.success == true) {
                val transactions = response.body()?.data
                if (transactions != null) {
                    Resource.Success(transactions)
                } else {
                    Resource.Error("Data transaksi kosong")
                }
            } else {
                val errorMsg = response.body()?.message
                    ?: "Gagal mengambil transaksi"
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server: ${e.message}")
        }
    }

    // GET TRANSACTION DETAIL
    suspend fun getTransactionById(id: Int): Resource<TransactionModel> {
        return try {
            val response = apiService.getTransactionById(id)

            if (response.isSuccessful && response.body()?.success == true) {
                val transaction = response.body()?.data
                if (transaction != null) {
                    Resource.Success(transaction)
                } else {
                    Resource.Error("Data transaksi kosong")
                }
            } else {
                val errorMsg = response.body()?.message
                    ?: "Transaksi tidak ditemukan"
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server: ${e.message}")
        }
    }

    // CREATE TRANSACTION
    suspend fun createTransaction(request: TransactionRequest): Resource<TransactionModel> {
        return try {
            val response = apiService.createTransaction(request)

            if (response.isSuccessful && response.body()?.success == true) {
                val transaction = response.body()?.data
                if (transaction != null) {
                    Resource.Success(transaction)
                } else {
                    Resource.Error("Data transaksi kosong")
                }
            } else {
                val errorMsg = response.body()?.message
                    ?: "Transaksi gagal dibuat"
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server: ${e.message}")
        }
    }
}