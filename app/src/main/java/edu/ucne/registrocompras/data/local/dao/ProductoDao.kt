package edu.ucne.registrocompras.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrocompras.data.local.entities.ProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Upsert
    suspend fun addProducto(productoEntity: ProductoEntity)

    @Query("""
        SELECT * FROM Productos
        WHERE proveedorId = :productoId
        LIMIT 1
    """)
    suspend fun getProducto(productoId: Int): ProductoEntity?

    @Delete
    suspend fun deleteProducto(productoEntity: ProductoEntity)

    @Query("SELECT * FROM Productos")
    fun getProductos(): Flow<List<ProductoEntity>>
}