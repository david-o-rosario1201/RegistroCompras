package edu.ucne.registrocompras.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrocompras.data.local.entities.CompraEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompraDao {
    @Upsert
    suspend fun addCompra(compraEntity: CompraEntity)

    @Query("""
        SELECT * FROM Compras
        WHERE compraId = :compraId
        LIMIT 1
    """)
    suspend fun getCompra(compraId: Int): CompraEntity?

    @Delete
    suspend fun deleteCompra(compraEntity: CompraEntity)

    @Query("SELECT * FROM Compras")
    fun getCompras(): Flow<List<CompraEntity>>
}