package edu.ucne.registrocompras.presentation.compra

import edu.ucne.registrocompras.data.local.entities.CompraEntity
import edu.ucne.registrocompras.data.local.entities.ProductoEntity
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

data class CompraUiState(
    val compraId: Int? = null,
    val fechaCompra: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now())),
    val productoId: Int? = 0,
    val cantidad: String? = "",
    val precioUnitario: String? = "",
    val totalCompra: String? = "",
    val compras: List<CompraEntity> = emptyList(),
    val productos: List<ProductoEntity> = emptyList(),
    val errorProductoId: String? = "",
    val errorCantidad: String? = "",
    val errorPrecioUnitario: String? = "",
    val errorTotalCompra: String? = "",
    val errorCargar: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false
)
