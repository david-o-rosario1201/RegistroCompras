package edu.ucne.registrocompras.data.remote.dto

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

data class CategoriaDto(
    val categoriaId: Int?,
    val descripcion: String,
    val fechaCreacion: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now()))
)
