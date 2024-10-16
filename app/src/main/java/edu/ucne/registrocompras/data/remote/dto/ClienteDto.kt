package edu.ucne.registrocompras.data.remote.dto

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

data class ClienteDto(
    val clienteId: Int?,
    val fechaCreacion: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now())),
    val nombre: String,
    val cedula: String,
    val direccion: String,
    val telefono: String
)
