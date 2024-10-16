package edu.ucne.registrocompras.presentation.compra

sealed interface CompraUiEvent {
    data class CompraIdChanged(val compraId: Int): CompraUiEvent
    data class ProductoIdChanged(val productoId: Int): CompraUiEvent
    data class CantidadChanged(val cantidad: Int): CompraUiEvent
    data class PrecioUnitarioChanged(val precioUnitario: Float): CompraUiEvent
    data class SelectedCompra(val compraId: Int): CompraUiEvent
    data object Save: CompraUiEvent
    data object Delete: CompraUiEvent
}