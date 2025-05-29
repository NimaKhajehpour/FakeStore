package com.nima.fakestore.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.fakestore.components.ProductListItem
import com.nima.fakestore.models.product.Product
import com.nima.fakestore.navigation.Screens
import com.nima.fakestore.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {

    var products: List<Product>? by remember {
        mutableStateOf(null)
    }
    var toggle by remember {
        mutableStateOf(false)
    }
    var error by remember {
        mutableStateOf("")
    }
    var isRefreshing by remember {
        mutableStateOf(false)
    }

    LaunchedEffect (toggle){
        try {
            products = viewModel.getProducts()
            isRefreshing = false
        }catch (e: Exception){
            error = e.message ?: "something went wrong"
        }
    }

    if (products == null && error.isBlank()){
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator()
            Text("Fetching Data, Please Wait...")
        }
    }

    if (error.isNotBlank()){
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(error)
            Button(
                onClick = {
                    toggle = !toggle
                    error = ""
                    products = null
                }
            ) {
                Text("Retry")
            }
        }
    }

    if (products != null && error.isBlank()){
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                toggle = !toggle
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyVerticalStaggeredGrid (
                modifier = Modifier.fillMaxWidth(),
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.Center,
            ){
                items(items = products!!, key = {
                    it.id
                }){
                    ProductListItem(
                        product = it
                    ) { id ->
                        navController.navigate(Screens.ProductScreen.name+"/$id")
                    }
                }
            }
        }
    }

}