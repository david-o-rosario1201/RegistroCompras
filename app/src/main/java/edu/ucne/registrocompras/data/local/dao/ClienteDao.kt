package edu.ucne.registrocompras.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrocompras.data.local.entities.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Upsert
    suspend fun addCliente(clienteEntity: ClienteEntity)

    @Query("""
        SELECT * FROM Clientes
        WHERE clienteId = :clienteId
        LIMIT 1
    """)
    suspend fun getCliente(clienteId: Int): ClienteEntity?

    @Delete
    suspend fun deleteCliente(clienteEntity: ClienteEntity)

    @Query("SELECT * FROM Clientes")
    fun getClientes(): Flow<List<ClienteEntity>>
}