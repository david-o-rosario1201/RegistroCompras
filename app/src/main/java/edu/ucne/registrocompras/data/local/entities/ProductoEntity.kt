package edu.ucne.registrocompras.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(
    tableName = "Productos",
    foreignKeys = [
        ForeignKey(
            entity = CategoriaEntity::class,
            parentColumns = ["categoriaId"],
            childColumns = ["categoriaId"]
        ),
        ForeignKey(
            entity = ProveedorEntity::class,
            parentColumns = ["proveedorId"],
            childColumns = ["proveedorId"]
        )
    ]
)
data class ProductoEntity(
    @PrimaryKey
    val productoId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val fechaCreacion: Date = Date.from(Instant.now()),
    val categoriaId: Int = 0,
    val proveedorId: Int = 0,
    val precio: Float = 0.0f
)
