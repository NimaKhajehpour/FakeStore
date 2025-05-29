package com.nima.fakestore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.nima.fakestore.models.product.Product
import com.nima.fakestore.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    id: Int?
) {
    var product: Product? by remember {
        mutableStateOf(null)
    }
    var toggle by remember {
        mutableStateOf(false)
    }
    var error by remember {
        mutableStateOf("")
    }

    LaunchedEffect (toggle, id){
        if (id != null){
            try{
                product = viewModel.getProduct(id)
            }catch (e: Exception){
                error = e.message ?: ""
            }
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
                },
            ) {
                Text("Retry")
            }
        }
    }
    if (error.isBlank() && product == null){
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator()
        }
    }
    if (error.isBlank() && product != null){
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text("Price: $${product!!.price}", modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                        )
                }
            }
        ){ padding ->
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 16.dp)
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        SubcomposeAsyncImage(
                            model = product!!.image,
                            contentDescription = null,
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.padding(10.dp),
                            success = {
                                SubcomposeAsyncImageContent()
                            },
                        )
                    }
                    Badge(
                        modifier = Modifier.align(Alignment.BottomStart)
                            .padding(5.dp),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ){
                        Text("Rate: ${product!!.rating.rate}", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(5.dp))
                    }
                    Badge(
                        modifier = Modifier.align(Alignment.TopEnd)
                            .padding(5.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ){
                        Text("#${product!!.category}", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(5.dp))
                    }
                }
                Text(product!!.title, style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Text(product!!.description, style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                )

            }
        }
    }
}