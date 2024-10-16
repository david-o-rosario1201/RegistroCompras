package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.local.dao.ClienteDao
import edu.ucne.registrocompras.data.local.entities.ClienteEntity
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ClienteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val clienteDao: ClienteDao
) {
    suspend fun addCliente(clienteDto: ClienteDto) = remoteDataSource.addCliente(clienteDto)

    suspend fun getCliente(clienteId: Int) = remoteDataSource.getCliente(clienteId)

    suspend fun deleteCliente(clienteId: Int) = remoteDataSource.deleteCliente(clienteId)

    suspend fun updateCliente(clienteId: Int, clienteDto: ClienteDto) = remoteDataSource.updateCliente(clienteId, clienteDto)

    fun getClientes(): Flow<Resource<List<ClienteEntity>>> = flow{
        try {
            emit(Resource.Loading())
            val clientes = remoteDataSource.getClientes()

            clientes.forEach {
                clienteDao.addCliente(
                    it.toClienteEntity()
                )
            }

            clienteDao.getClientes().collect { clientesLocal ->
                emit(Resource.Success(clientesLocal))
            }

        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))

            clienteDao.getClientes().collect { clientesLocal ->
                emit(Resource.Success(clientesLocal))
            }
        }
    }
}

private fun ClienteDto.toClienteEntity() = ClienteEntity (
    clienteId = clienteId,
    nombre = nombre,
    telefono = telefono,
    direccion = direccion,
    cedula = cedula,
    fechaCreacion = fechaCreacion
)