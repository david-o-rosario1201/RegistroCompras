package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.local.dao.CompraDao
import edu.ucne.registrocompras.data.local.entities.CompraEntity
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.CompraDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CompraRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val compraDao: CompraDao
){
    suspend fun addCompra(compraDto: CompraDto) = remoteDataSource.addCompra(compraDto)

    suspend fun getCompra(compraId: Int) = remoteDataSource.getCompra(compraId)

    suspend fun deleteCompra(compraId: Int) = remoteDataSource.deleteCompra(compraId)

    suspend fun updateCompra(compraId: Int, compraDto: CompraDto) = remoteDataSource.updateCompra(compraId, compraDto)

    fun getCompras(): Flow<Resource<List<CompraEntity>>> = flow{
        try {
            emit(Resource.Loading())
            val compras = remoteDataSource.getCompras()

            compras.forEach {
                compraDao.addCompra(
                    it.toCompraEntity()
                )
            }

            compraDao.getCompras().collect { comprasLocal ->
                emit(Resource.Success(comprasLocal))
            }

        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))

            compraDao.getCompras().collect { comprasLocal ->
                emit(Resource.Success(comprasLocal))
            }
        }
    }
}

private fun CompraDto.toCompraEntity() = CompraEntity (
    compraId = compraId,
    fechaCompra = fechaCompra,
    productoId = productoId,
    cantidad = cantidad,
    precioUnitario = precioUnitario,
    totalCompra = totalCompra
)