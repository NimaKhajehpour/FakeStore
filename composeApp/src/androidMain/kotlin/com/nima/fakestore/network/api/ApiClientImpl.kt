package com.nima.fakestore.network.api

import android.util.Log
import com.nima.fakestore.models.product.Product
import com.nima.fakestore.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.path

class ApiClientImpl(private val httpClient: HttpClient): ApiClient {
    override suspend fun getProducts(): List<Product> {
        val response = httpClient.get{
            url{
                protocol = URLProtocol.HTTPS
                host = Constants.BASE_URL
                path("products")
            }
        }
        if (!response.status.isSuccess()){
            throw Exception("Try Again, Request Failed")
        }
        return response.body()
    }

    override suspend fun getProduct(id: Int): Product {
        val response = httpClient.get{
            url{
                protocol = URLProtocol.HTTPS
                host = Constants.BASE_URL
                path("products/$id")
            }
        }
        if (!response.status.isSuccess()){
            throw Exception("Try Again, Request Failed")

        }
        return response.body()
    }
}