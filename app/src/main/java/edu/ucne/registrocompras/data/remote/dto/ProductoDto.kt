package edu.ucne.registrocompras.data.remote.dto

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

data class ProductoDto(
    val productoId: Int?,
    val nombre: String,
    val descripcion: String,
    val fechaCreacion: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now())),
    val categoriaId: Int,
    val proveedorId: Int,
    val precio: Float
)
