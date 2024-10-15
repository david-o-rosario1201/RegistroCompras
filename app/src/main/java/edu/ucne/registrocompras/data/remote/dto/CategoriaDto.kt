package edu.ucne.registrocompras.data.remote.dto

import java.time.Instant
import java.util.Date

data class CategoriaDto(
    val categoriaId: Int?,
    val descripcion: String,
    val fechaCreacion: Date = Date.from(Instant.now())
)
