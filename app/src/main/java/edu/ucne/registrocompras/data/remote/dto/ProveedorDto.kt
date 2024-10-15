package edu.ucne.registrocompras.data.remote.dto

import java.time.Instant
import java.util.Date

data class ProveedorDto(
    val proveedorId: Int?,
    val fechaCreacion: Date = Date.from(Instant.now()),
    val nombre: String,
    val rnc: String,
    val direccion: String,
    val email: String
)
