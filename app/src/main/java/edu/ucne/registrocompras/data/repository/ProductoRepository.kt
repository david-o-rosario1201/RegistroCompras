package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.local.dao.ProductoDao
import edu.ucne.registrocompras.data.local.entities.ProductoEntity
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ProductoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val productoDao: ProductoDao
) {
    suspend fun addProducto(productoDto: ProductoDto) = remoteDataSource.addProducto(productoDto)

    suspend fun getProducto(productoId: Int) = remoteDataSource.getProducto(productoId)

    suspend fun deleteProducto(productoId: Int) = remoteDataSource.deleteProducto(productoId)

    suspend fun updateProducto(productoId: Int, productoDto: ProductoDto) = remoteDataSource.updateProducto(productoId, productoDto)

    fun getProductos(): Flow<Resource<List<ProductoEntity>>> = flow{
        try {
            emit(Resource.Loading())
            val productos = remoteDataSource.getProductos()

            productos.forEach {
                productoDao.addProducto(
                    it.toProductoEntity()
                )
            }

            productoDao.getProductos().collect { productosLocal ->
                emit(Resource.Success(productosLocal))
            }

        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))

            productoDao.getProductos().collect { productosLocal ->
                emit(Resource.Success(productosLocal))
            }
        }
    }
}

private fun ProductoDto.toProductoEntity() = ProductoEntity (
    productoId = productoId,
    nombre = nombre,
    precio = precio,
    categoriaId = categoriaId,
    proveedorId = proveedorId,
    fechaCreacion = fechaCreacion,
    descripcion = descripcion
)