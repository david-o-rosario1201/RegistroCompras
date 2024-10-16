package edu.ucne.registrocompras.data.remote.dto

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

data class CompraDto(
    val compraId: Int?,
    val fechaCompra: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now())),
    val productoId: Int,
    val cantidad: Int,
    val precioUnitario: Float,
    val totalCompra: Float
)
