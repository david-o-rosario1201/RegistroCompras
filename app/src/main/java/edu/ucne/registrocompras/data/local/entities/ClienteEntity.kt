package edu.ucne.registrocompras.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@Entity(tableName = "Clientes")
data class ClienteEntity(
    @PrimaryKey
    val clienteId: Int? = null,
    val fechaCreacion: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now())),
    val nombre: String = "",
    val cedula: String = "",
    val direccion: String = "",
    val telefono: String = ""
)
