package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.local.dao.CompraDao
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.dto.CompraDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test

class CompraRepositoryTest {

    @Test
    fun addCompra() = runTest{
        //Given
        val compraDto = CompraDto(
            compraId = 1,
            productoId = 1,
            cantidad = 1,
            precioUnitario = 1.0f,
            totalCompra = 1.0f,
            fechaCompra = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val compraDao = mockk<CompraDao>()

        val repository = CompraRepository(remoteDataSource, compraDao)

        //When
        val result = repository.addCompra(compraDto)

        //Then
        assertEquals(compraDto, result)
    }

    @Test
    fun getCompra() = runTest {
        //Given
        val compraDto = CompraDto(
            compraId = 1,
            productoId = 1,
            cantidad = 1,
            precioUnitario = 1.0f,
            totalCompra = 1.0f,
            fechaCompra = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val compraDao = mockk<CompraDao>()

        val repository = CompraRepository(remoteDataSource, compraDao)

        //When
        val result = repository.getCompra(1)

        //Then
        assertEquals(compraDto, result)
    }

    @Test
    fun deleteCompra() = runTest{
        //Given
        val compraDto = CompraDto(
            compraId = 1,
            productoId = 1,
            cantidad = 1,
            precioUnitario = 1.0f,
            totalCompra = 1.0f,
            fechaCompra = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val compraDao = mockk<CompraDao>()

        val repository = CompraRepository(remoteDataSource, compraDao)

        //coEvery { remoteDataSource.deleteCompra(1) } returns compraDto

        //When
        val result = repository.deleteCompra(1)

        //Then
        assertEquals(compraDto, result)
    }

    @Test
    fun updateCompra() = runTest {
        //Given
        val compraDto = CompraDto(
            compraId = 5,
            productoId = 1,
            cantidad = 1,
            precioUnitario = 1.0f,
            totalCompra = 1.0f,
            fechaCompra = "2024-10-14T03:06:2"
        )
    }

    @Test
    fun getCompras() = runTest {
        //Given
        val compras = listOf(
            CompraDto(
                compraId = 5,
                productoId = 1,
                cantidad = 1,
                precioUnitario = 1.0f,
                totalCompra = 1.0f,
                fechaCompra = "2024-10-14T03:06:27"
            )
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val compraDao = mockk<CompraDao>()

        val repository = CompraRepository(remoteDataSource, compraDao)

        coEvery { remoteDataSource.getCompras() } returns compras

        //When
        val result = repository.getCompras()

        //Then
        assertEquals(compras, result)
    }
}