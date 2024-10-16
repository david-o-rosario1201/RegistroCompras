package edu.ucne.registrocompras.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@Entity(tableName = "Proveedores")
data class ProveedorEntity(
    @PrimaryKey
    val proveedorId: Int? = null,
    val fechaCreacion: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now())),
    val nombre: String = "",
    val rnc: String = "",
    val direccion: String = "",
    val email: String = ""
)
