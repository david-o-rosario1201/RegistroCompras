package edu.ucne.registrocompras.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "Clientes")
data class ClienteEntity(
    @PrimaryKey
    val clienteId: Int? = null,
    val fechaCreacion: Date = Date.from(Instant.now()),
    val nombre: String = "",
    val cedula: String = "",
    val direccion: String = "",
    val telefono: String = ""
)
