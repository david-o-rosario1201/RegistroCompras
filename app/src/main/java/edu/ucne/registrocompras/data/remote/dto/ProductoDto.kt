package edu.ucne.registrocompras.data.remote.dto

import java.time.Instant
import java.util.Date

data class ProductoDto(
    val productoId: Int?,
    val nombre: String,
    val descripcion: String,
    val fechaCreacion: Date = Date.from(Instant.now()),
    val categoriaId: Int,
    val proveedorId: Int,
    val precio: Float
)
