package com.nima.fakestore.network.api

import com.nima.fakestore.models.product.Product
import io.ktor.client.statement.HttpResponse

interface ApiClient {
    suspend fun getProducts(): List<Product>
    suspend fun getProduct(id: Int): Product
}