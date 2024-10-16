package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.local.dao.ProveedorDao
import edu.ucne.registrocompras.data.local.entities.ProveedorEntity
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ProveedorDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ProveedorRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val proveedorDao: ProveedorDao
) {
    suspend fun addProveedor(proveedorDto: ProveedorDto) = remoteDataSource.addProveedor(proveedorDto)

    suspend fun getProveedor(proveedorId: Int) = remoteDataSource.getProveedor(proveedorId)

    suspend fun deleteProveedor(proveedorId: Int) = remoteDataSource.deleteProveedor(proveedorId)

    suspend fun updateProveedor(proveedorId: Int, proveedorDto: ProveedorDto) = remoteDataSource.updateProveedor(proveedorId, proveedorDto)

    fun getProveedores(): Flow<Resource<List<ProveedorEntity>>> = flow{
        try {
            emit(Resource.Loading())
            val proveedores = remoteDataSource.getProveedores()

            proveedores.forEach {
                proveedorDao.addProveedor(
                    it.toProveedorEntity()
                )
            }

            proveedorDao.getProveedores().collect { proveedoresLocal ->
                emit(Resource.Success(proveedoresLocal))
            }
        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))

            proveedorDao.getProveedores().collect { proveedoresLocal ->
                emit(Resource.Success(proveedoresLocal))
            }
        }
    }
}

private fun ProveedorDto.toProveedorEntity() = ProveedorEntity (
    proveedorId = proveedorId,
    nombre = nombre,
    rnc = rnc,
    direccion = direccion,
    email = email,
    fechaCreacion = fechaCreacion
)