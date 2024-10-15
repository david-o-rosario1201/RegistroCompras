package edu.ucne.registrocompras.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "Categorias")
data class CategoriaEntity(
    @PrimaryKey
    val categoriaId: Int? = null,
    val descripcion: String = "",
    val fechaCreacion: Date = Date.from(Instant.now())
)
