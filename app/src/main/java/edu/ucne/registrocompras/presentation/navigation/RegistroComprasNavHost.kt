package edu.ucne.registrocompras.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RegistroComprasNavHots(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {  }
        composable<Screen.ProductoListScreen> {  }
        composable<Screen.ProductoScreen> {  }
        composable<Screen.ClienteListScreen> {  }
        composable<Screen.ClienteScreen> {  }
        composable<Screen.CategoriaListScreen> {  }
        composable<Screen.CategoriaScreen> {  }
        composable<Screen.ProductoListScreen> {  }
        composable<Screen.ProductoScreen> {  }
        composable<Screen.CompraListScreen> {  }
        composable<Screen.CompraScreen> {  }
    }
}