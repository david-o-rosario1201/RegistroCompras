package edu.ucne.registrocompras.data.remote.dto

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import kotlin.text.format

data class ProveedorDto(
    val proveedorId: Int?,
    val fechaCreacion: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now())),
    val nombre: String,
    val rnc: String,
    val direccion: String,
    val email: String
)
