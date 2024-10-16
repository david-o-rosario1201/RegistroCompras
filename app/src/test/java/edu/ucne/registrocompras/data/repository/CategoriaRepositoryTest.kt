package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.local.dao.CategoriaDao
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.dto.CategoriaDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test

class CategoriaRepositoryTest {

    @Test
    fun addCategoria() = runTest {
        //Given
        val categoriaDto = CategoriaDto(
            categoriaId = 15,
            descripcion = "Limpieza"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val categoriaDao = mockk<CategoriaDao>()

        val repository = CategoriaRepository(remoteDataSource, categoriaDao)

        coEvery { remoteDataSource.addCategoria(categoriaDto) } returns categoriaDto

        //When
        val result = repository.addCategoria(categoriaDto)

        //Then
        assertEquals(categoriaDto, result)
    }

    @Test
    fun getCategoria() = runTest{
        //Given
        val categoriaDto = CategoriaDto(
            categoriaId = 1,
            descripcion = "Limpieza"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val categoriaDao = mockk<CategoriaDao>()

        val repository = CategoriaRepository(remoteDataSource, categoriaDao)

        coEvery { remoteDataSource.getCategoria(1) } returns categoriaDto

        //When
        val result = repository.getCategoria(1)

        //Then
        assertEquals(categoriaDto, result)
    }

    @Test
    fun deleteCategoria() = runTest {
        //Given
        val categoriaDto = CategoriaDto(
            categoriaId = 5,
            descripcion = "Limpieza"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val categoriaDao = mockk<CategoriaDao>()

        val repository = CategoriaRepository(remoteDataSource, categoriaDao)

        //coEvery { remoteDataSource.deleteCategoria(5) } returns categoriaDto

        //When
        val result = repository.deleteCategoria(5)

        //Then
        assertEquals(categoriaDto, result)
    }

    @Test
    fun updateCategoria() = runTest{
        //Given
        val categoriaDto = CategoriaDto(
            categoriaId = 5,
            descripcion = "Limpieza"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val categoriaDao = mockk<CategoriaDao>()

        val repository = CategoriaRepository(remoteDataSource, categoriaDao)

        //coEvery { remoteDataSource.updateCategoria(5, categoriaDto) } returns categoriaDto

        //When
        val result = repository.updateCategoria(5, categoriaDto)

        //Then
        assertEquals(categoriaDto, result)
    }

    @Test
    fun getCategorias() = runTest {
        //Given
        val categorias = listOf(
            CategoriaDto(
                categoriaId = 5,
                descripcion = "Limpieza"
            )
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val categoriaDao = mockk<CategoriaDao>()

        val repository = CategoriaRepository(remoteDataSource, categoriaDao)

        coEvery { remoteDataSource.getCategorias() } returns categorias

        //When
        val result = repository.getCategorias()

        //Then
        assertEquals(categorias, result)
    }
}