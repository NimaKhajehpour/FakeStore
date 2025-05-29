package com.nima.fakestore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nima.fakestore.screens.HomeScreen
import com.nima.fakestore.screens.ProductScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, Screens.HomeScreen.name){
        composable(Screens.HomeScreen.name){
            HomeScreen(navController, koinViewModel())
        }
        composable(Screens.ProductScreen.name+"/{id}",
            arguments = listOf(navArgument("id"){type = NavType.IntType})
            ){
            ProductScreen(navController, koinViewModel(), it.arguments?.getInt("id"))
        }
    }
}