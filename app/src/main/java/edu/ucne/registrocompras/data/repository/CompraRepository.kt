package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.CompraDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CompraRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    suspend fun addCompra(compraDto: CompraDto) = remoteDataSource.addCompra(compraDto)

    suspend fun getCompra(compraId: Int) = remoteDataSource.getCompra(compraId)

    suspend fun deleteCompra(compraId: Int) = remoteDataSource.deleteCompra(compraId)

    suspend fun updateCompra(compraId: Int) = remoteDataSource.updateCompra(compraId)

    fun getCompras(): Flow<Resource<List<CompraDto>>> = flow{
        try {
            emit(Resource.Loading())
            val compras = remoteDataSource.getCompras()

            emit(Resource.Success(compras))
        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }
}