package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ClienteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {
    suspend fun addCliente(clienteDto: ClienteDto) = remoteDataSource.addCliente(clienteDto)

    suspend fun getCliente(clienteId: Int) = remoteDataSource.getCliente(clienteId)

    suspend fun deleteCliente(clienteId: Int) = remoteDataSource.deleteCliente(clienteId)

    suspend fun updateCliente(clienteId: Int, clienteDto: ClienteDto) = remoteDataSource.updateCliente(clienteId, clienteDto)

    fun getClientes(): Flow<Resource<List<ClienteDto>>> = flow{
        try {
            emit(Resource.Loading())
            val clientes = remoteDataSource.getClientes()

            emit(Resource.Success(clientes))
        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }
}