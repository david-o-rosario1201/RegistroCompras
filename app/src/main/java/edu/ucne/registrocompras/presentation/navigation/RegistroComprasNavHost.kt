package edu.ucne.registrocompras.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrocompras.presentation.categoria.CategoriaListScreen
import edu.ucne.registrocompras.presentation.categoria.CategoriaScreen
import edu.ucne.registrocompras.presentation.cliente.ClienteListScreen
import edu.ucne.registrocompras.presentation.cliente.ClienteScreen
import edu.ucne.registrocompras.presentation.compra.CompraListScreen
import edu.ucne.registrocompras.presentation.compra.CompraScreen
import edu.ucne.registrocompras.presentation.home.HomeScreen
import edu.ucne.registrocompras.presentation.producto.ProductoListScreen
import edu.ucne.registrocompras.presentation.producto.ProductoScreen
import edu.ucne.registrocompras.presentation.proveedor.ProveedorListScreen
import edu.ucne.registrocompras.presentation.proveedor.ProveedorScreen
import edu.ucne.registrocompras.ui.theme.util.NavigationItem
import edu.ucne.registrocompras.ui.theme.util.Route
import kotlinx.coroutines.launch

@Composable
fun RegistroComprasNavHots(
    navHostController: NavHostController,
    items: List<NavigationItem>
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "   Menú",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            if(item.route == Route.Home)
                                navHostController.navigate(Screen.HomeScreen)
                            if(item.route == Route.Proveedores)
                                navHostController.navigate(Screen.ProveedorListScreen)
                            if(item.route == Route.Clientes)
                                navHostController.navigate(Screen.ClienteListScreen)
                            if(item.route == Route.Categorias)
                                navHostController.navigate(Screen.CategoriaListScreen)
                            if(item.route == Route.Productos)
                                navHostController.navigate(Screen.ProductoListScreen)
                            if(item.route == Route.Compras)
                                navHostController.navigate(Screen.CompraListScreen)

                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if(index == selectedItemIndex)
                                    item.selectedIcon
                                else
                                    item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.HomeScreen
        ) {
            composable<Screen.HomeScreen> {
                HomeScreen(
                    drawerState = drawerState,
                    scope = scope,
                    navHostController = navHostController
                )
            }
            composable<Screen.ProveedorListScreen> {
                ProveedorListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onClickProveedor = { proveedorId ->
                        navHostController.navigate(Screen.ProveedorScreen(proveedorId))
                    },
                    onAddProveedor = {
                        navHostController.navigate(Screen.ProveedorScreen(0))
                    }
                )
            }
            composable<Screen.ProveedorScreen> { argumentos ->
                val proveedorId = argumentos.toRoute<Screen.ProveedorScreen>().proveedorId
                ProveedorScreen(
                    proveedorId = proveedorId,
                    goProveedorList = {
                        navHostController.navigate(Screen.ProveedorListScreen)
                    }
                )
            }
            composable<Screen.ClienteListScreen> {
                ClienteListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onClickCliente = { clienteId ->
                        navHostController.navigate(Screen.ClienteScreen(clienteId))
                    },
                    onAddCliente = {
                        navHostController.navigate(Screen.ClienteScreen(0))
                    }
                )
            }
            composable<Screen.ClienteScreen> { argumentos ->
                val clienteId = argumentos.toRoute<Screen.ClienteScreen>().clienteId
                ClienteScreen(
                    clienteId = clienteId,
                    goClienteList = {
                        navHostController.navigate(Screen.ClienteListScreen)
                    }
                )
            }
            composable<Screen.CategoriaListScreen> {
                CategoriaListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onClickCategoria = { categoriaId ->
                        navHostController.navigate(Screen.CategoriaScreen(categoriaId))
                    },
                    onAddCategoria = {
                        navHostController.navigate(Screen.CategoriaScreen(0))
                    }
                )
            }
            composable<Screen.CategoriaScreen> { argumentos ->
                val categoriaId = argumentos.toRoute<Screen.CategoriaScreen>().categoriaId
                CategoriaScreen(
                    categoriaId = categoriaId,
                    goCategoriaList = {
                        navHostController.navigate(Screen.CategoriaListScreen)
                    }
                )
            }
            composable<Screen.ProductoListScreen> {
                ProductoListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onClickProducto = { productoId ->
                        navHostController.navigate(Screen.ProductoScreen(productoId))
                    },
                    onAddProducto = {
                        navHostController.navigate(Screen.ProductoScreen(0))
                    }
                )
            }
            composable<Screen.ProductoScreen> { argumentos ->
                val productoId = argumentos.toRoute<Screen.ProductoScreen>().productoId
                ProductoScreen(
                    productoId = productoId,
                    goProductoList = {
                        navHostController.navigate(Screen.ProductoListScreen)
                    }
                )
            }
            composable<Screen.CompraListScreen> {
                CompraListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onClickCompra = { compraId ->
                        navHostController.navigate(Screen.CompraScreen(compraId))
                    },
                    onAddCompra = {
                        navHostController.navigate(Screen.CompraScreen(0))
                    }
                )
            }
            composable<Screen.CompraScreen> { argumentos ->
                val compraId = argumentos.toRoute<Screen.CompraScreen>().compraId
                CompraScreen(
                    compraId = compraId,
                    goCompraList = {
                        navHostController.navigate(Screen.CompraListScreen)
                    }
                )
            }
        }
    }
}