package com.nima.fakestore.viewmodels

import androidx.lifecycle.ViewModel
import com.nima.fakestore.repository.Repository

class HomeViewModel(private val repository: Repository): ViewModel() {
    suspend fun getProducts() = repository.getProducts()
}