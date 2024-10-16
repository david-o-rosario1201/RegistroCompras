package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.local.dao.CategoriaDao
import edu.ucne.registrocompras.data.local.entities.CategoriaEntity
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.CategoriaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CategoriaRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val categoriaDao: CategoriaDao
){
    suspend fun addCategoria(categoriaDto: CategoriaDto) = remoteDataSource.addCategoria(categoriaDto)

    suspend fun getCategoria(categoriaId: Int) = remoteDataSource.getCategoria(categoriaId)

    suspend fun deleteCategoria(categoriaId: Int) = remoteDataSource.deleteCategoria(categoriaId)

    suspend fun updateCategoria(categoriaId: Int, categoriaDto: CategoriaDto) = remoteDataSource.updateCategoria(categoriaId, categoriaDto)

    fun getCategorias(): Flow<Resource<List<CategoriaEntity>>> = flow{
        try {
            emit(Resource.Loading())
            val categorias = remoteDataSource.getCategorias()

            categorias.forEach {
                categoriaDao.addCategoria(
                    it.toCategoriaEntity()
                )
            }

            categoriaDao.getCategorias().collect { categoriasLocal ->
                emit(Resource.Success(categoriasLocal))
            }

        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))

            categoriaDao.getCategorias().collect { categoriasLocal ->
                emit(Resource.Success(categoriasLocal))
            }
        }
    }
}

private fun CategoriaDto.toCategoriaEntity() = CategoriaEntity (
    categoriaId = categoriaId,
    descripcion = descripcion,
    fechaCreacion = fechaCreacion
)