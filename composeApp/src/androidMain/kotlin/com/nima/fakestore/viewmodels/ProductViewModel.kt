package com.nima.fakestore.viewmodels

import androidx.lifecycle.ViewModel
import com.nima.fakestore.repository.Repository

class ProductViewModel(private val repository: Repository): ViewModel() {
    suspend fun getProduct(id: Int) = repository.getProduct(id)
}