package edu.ucne.registrocompras.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrocompras.data.local.entities.ProveedorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProveedorDao {
    @Upsert
    suspend fun addProveedor(proveedorEntity: ProveedorEntity)

    @Query("""
        SELECT * FROM Proveedores
        WHERE proveedorId = :proveedorId
        LIMIT 1
    """)
    suspend fun getProveedor(proveedorId: Int): ProveedorEntity?

    @Delete
    suspend fun deleteProveedor(proveedorEntity: ProveedorEntity)


    @Query("SELECT * FROM Proveedores")
    fun getProveedores(): Flow<List<ProveedorEntity>>
}