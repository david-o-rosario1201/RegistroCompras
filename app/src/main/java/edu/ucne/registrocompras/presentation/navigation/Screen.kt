package edu.ucne.registrocompras.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ProveedorListScreen: Screen()

    @Serializable
    data class ProveedorScreen(val proveedorId: Int): Screen()

    @Serializable
    data object ClienteListScreen: Screen()

    @Serializable
    data class ClienteScreen(val clienteId: Int): Screen()

    @Serializable
    data object CategoriaListScreen: Screen()

    @Serializable
    data class CategoriaScreen(val categoriaId: Int): Screen()

    @Serializable
    data object ProductoListScreen: Screen()

    @Serializable
    data class ProductoScreen(val productoId: Int): Screen()

    @Serializable
    data object CompraListScreen: Screen()

    @Serializable
    data class CompraScreen(val compraId: Int): Screen()

    @Serializable
    data object HomeScreen: Screen()
}