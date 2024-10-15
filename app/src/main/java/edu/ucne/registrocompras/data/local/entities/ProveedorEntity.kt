package edu.ucne.registrocompras.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "Proveedores")
data class ProveedorEntity(
    @PrimaryKey
    val proveedorId: Int? = null,
    val fechaCreacion: Date = Date.from(Instant.now()),
    val nombre: String = "",
    val rnc: String = "",
    val direccion: String = "",
    val email: String = ""
)
