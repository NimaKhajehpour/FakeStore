package com.nima.fakestore.repository

import com.nima.fakestore.network.api.ApiClient

class Repository(private val apiClient: ApiClient) {

    suspend fun getProducts() = apiClient.getProducts()

    suspend fun getProduct(id: Int) = apiClient.getProduct(id)
}