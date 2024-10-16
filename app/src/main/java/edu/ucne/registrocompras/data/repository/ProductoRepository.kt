package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ProductoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun addProducto(productoDto: ProductoDto) = remoteDataSource.addProducto(productoDto)

    suspend fun getProducto(productoId: Int) = remoteDataSource.getProducto(productoId)

    suspend fun deleteProducto(productoId: Int) = remoteDataSource.deleteProducto(productoId)

    suspend fun updateProducto(productoId: Int, productoDto: ProductoDto) = remoteDataSource.updateProducto(productoId, productoDto)

    fun getProductos(): Flow<Resource<List<ProductoDto>>> = flow{
        try {
            emit(Resource.Loading())
            val productos = remoteDataSource.getProductos()

            emit(Resource.Success(productos))
        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }
}