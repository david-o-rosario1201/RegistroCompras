package edu.ucne.registrocompras.presentation.producto

sealed interface ProductoUiEvent {
    data class ProductoIdChange(val productoId: Int): ProductoUiEvent
    data class NombreChange(val nombre: String): ProductoUiEvent
    data class DescripcionChange(val descripcion: String): ProductoUiEvent
    data class CategoriaIdChange(val categoriaId: Int): ProductoUiEvent
    data class ProveedorIdChange(val proveedorId: Int): ProductoUiEvent
    data class PrecioChange(val precio: String): ProductoUiEvent
    data class SelectedProducto(val productoId: Int): ProductoUiEvent
    data object Save: ProductoUiEvent
    data object Delete: ProductoUiEvent

}