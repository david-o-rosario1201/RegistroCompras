@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.registrocompras.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import edu.ucne.registrocompras.R
import edu.ucne.registrocompras.presentation.navigation.Screen
import edu.ucne.registrocompras.ui.theme.util.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navHostController: NavHostController,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        }
    ){ values ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(values)
            .background(Color.White))
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CardHome(
                        painter = painterResource(R.drawable.proveedores_background),
                        contentDescription = "Proveedores",
                        title = "Proveedores",
                        route = Route.Proveedores,
                        navController = navHostController
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    CardHome(
                        painter = painterResource(R.drawable.cliente_fondo),
                        contentDescription = "Compras",
                        title = "Compras",
                        route = Route.Compras,
                        navController = navHostController
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    CardHome(
                        painter = painterResource(R.drawable.categorias_background),
                        contentDescription = "Clientes",
                        title = "Clientes",
                        route = Route.Categorias,
                        navController = navHostController
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    CardHome(
                        painter = painterResource(R.drawable.productos_background),
                        contentDescription = "Productos",
                        title = "Productos",
                        route = Route.Productos,
                        navController = navHostController
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    CardHome(
                        painter = painterResource(R.drawable.compras_background),
                        contentDescription = "Compras",
                        title = "Compras",
                        route = Route.Compras,
                        navController = navHostController
                    )
                }
            }
        }
    }
}

@Composable
fun CardHome(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier,
    route: Route,
    navController: NavController
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if(route == Route.Home)
                    navController.navigate(Screen.HomeScreen)
                if(route == Route.Proveedores)
                    navController.navigate(Screen.ProveedorListScreen)
                if(route == Route.Clientes)
                    navController.navigate(Screen.ClienteListScreen)
                if(route == Route.Categorias)
                    navController.navigate(Screen.CategoriaListScreen)
                if(route == Route.Productos)
                    navController.navigate(Screen.ProductoListScreen)
                if(route == Route.Compras)
                    navController.navigate(Screen.CompraListScreen)
            },
        shape = RoundedCornerShape(15.dp)
    ){
        Box(
            modifier = Modifier
                .height(200.dp)
        ){
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ){
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}