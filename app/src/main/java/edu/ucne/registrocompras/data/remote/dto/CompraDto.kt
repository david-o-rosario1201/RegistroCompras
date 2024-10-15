package edu.ucne.registrocompras.data.remote.dto

import java.time.Instant
import java.util.Date

data class CompraDto(
    val compraId: Int?,
    val fechaCompra: Date = Date.from(Instant.now()),
    val productoId: Int,
    val cantidad: Int,
    val precioUnitario: Float,
    val totalCompra: Float
)
