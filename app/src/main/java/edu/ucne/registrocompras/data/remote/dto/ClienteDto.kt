package edu.ucne.registrocompras.data.remote.dto

import java.time.Instant
import java.util.Date

data class ClienteDto(
    val clienteId: Int?,
    val fechaCreacion: Date = Date.from(Instant.now()),
    val nombre: String,
    val cedula: String,
    val direccion: String,
    val telefono: String
)
