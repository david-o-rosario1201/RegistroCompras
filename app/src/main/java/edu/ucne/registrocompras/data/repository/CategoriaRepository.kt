package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.CategoriaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CategoriaRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    suspend fun addCategoria(categoriaDto: CategoriaDto) = remoteDataSource.addCategoria(categoriaDto)

    suspend fun getCategoria(categoriaId: Int) = remoteDataSource.getCategoria(categoriaId)

    suspend fun deleteCategoria(categoriaId: Int) = remoteDataSource.deleteCategoria(categoriaId)

    suspend fun updateCategoria(categoriaId: Int) = remoteDataSource.updateCategoria(categoriaId)

    fun getCategorias(): Flow<Resource<List<CategoriaDto>>> = flow{
        try {
            emit(Resource.Loading())
            val categorias = remoteDataSource.getCategorias()

            emit(Resource.Success(categorias))
        } catch (e: HttpException){
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }
}