package edu.ucne.registrocompras.presentation.compra

import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.CompraDto
import edu.ucne.registrocompras.data.remote.dto.ProductoDto
import java.time.Instant
import java.util.Date

data class CompraUiState(
    val compraId: Int? = null,
    val fechaCompra: Date = Date.from(Instant.now()),
    val productoId: Int? = 0,
    val cantidad: String? = "",
    val precioUnitario: String? = "",
    val totalCompra: String? = "",
    val compras: List<CompraDto> = emptyList(),
    val productos: List<ProductoDto> = emptyList(),
    val errorProductoId: String? = "",
    val errorCantidad: String? = "",
    val errorPrecioUnitario: String? = "",
    val errorTotalCompra: String? = "",
    val errorCargar: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false
)
