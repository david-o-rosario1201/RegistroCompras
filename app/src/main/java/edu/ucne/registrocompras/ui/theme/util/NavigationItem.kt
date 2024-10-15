package edu.ucne.registrocompras.ui.theme.util

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Route,
    val badgeCount: Int? = null
)

enum class Route{
    Home,
    Proveedores,
    Clientes,
    Categorias,
    Productos,
    Compras
}
