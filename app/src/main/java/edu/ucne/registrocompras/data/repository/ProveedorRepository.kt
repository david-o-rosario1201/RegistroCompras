package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ProveedorDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ProveedorRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun addProveedor(proveedorDto: ProveedorDto) = remoteDataSource.addProveedor(proveedorDto)

    suspend fun getProveedor(proveedorId: Int) = remoteDataSource.getProveedor(proveedorId)

    suspend fun deleteProveedor(proveedorId: Int) = remoteDataSource.deleteProveedor(proveedorId)

    suspend fun updateProveedor(proveedorId: Int) = remoteDataSource.updateProveedor(proveedorId)

    fun getProveedores(): Flow<Resource<List<ProveedorDto>>> = flow{
        try {
            emit(Resource.Loading())
            val proveedores = remoteDataSource.getProveedores()

            emit(Resource.Success(proveedores))
        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }
}