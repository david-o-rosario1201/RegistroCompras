package edu.ucne.registrocompras.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(
    tableName = "Compras",
    foreignKeys = [
        ForeignKey(
            entity = ProductoEntity::class,
            parentColumns = ["productoId"],
            childColumns = ["productoId"]
        )
    ]
)
data class CompraEntity(
    @PrimaryKey
    val compraId: Int? = null,
    val fechaCompra: Date = Date.from(Instant.now()),
    val productoId: Int = 0,
    val cantidad: Int = 0,
    val precioUnitario: Float = 0.0f,
    val totalCompra: Float = 0.0f
)
