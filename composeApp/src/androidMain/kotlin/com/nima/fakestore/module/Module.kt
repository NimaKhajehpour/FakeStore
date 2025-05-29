package com.nima.fakestore.module

import com.nima.fakestore.network.api.ApiClient
import com.nima.fakestore.network.api.ApiClientImpl
import com.nima.fakestore.repository.Repository
import com.nima.fakestore.viewmodels.HomeViewModel
import com.nima.fakestore.viewmodels.ProductViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(OkHttp){
            install(ContentNegotiation){
                json()
            }
        }
    }

    single <ApiClient>{
        ApiClientImpl(get())
    }

    single {
        Repository(get())
    }

    viewModel {
        HomeViewModel(get())
    }
    viewModel{
        ProductViewModel(get())
    }
}