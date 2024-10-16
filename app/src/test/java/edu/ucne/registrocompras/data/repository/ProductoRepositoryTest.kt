package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.local.dao.ProductoDao
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.dto.ProductoDto
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test

class ProductoRepositoryTest {

    @Test
    fun addProducto() = runTest{
        //Given
        val productoDto = ProductoDto(
            productoId = 5,
            nombre = "Leche",
            descripcion = "Leche entera",
            categoriaId = 1,
            proveedorId = 1,
            precio = 2.5f
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val productoDao = mockk<ProductoDao>()

        val repository = ProductoRepository(remoteDataSource, productoDao)

        //When
        val result = repository.addProducto(productoDto)

        //Then
        assertEquals(productoDto, result)
    }

    @Test
    fun getProducto() = runTest {
        //Given
        val productoDto = ProductoDto(
            productoId = 5,
            nombre = "Leche",
            descripcion = "Leche entera",
            categoriaId = 1,
            proveedorId = 1,
            precio = 2.5f
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val productoDao = mockk<ProductoDao>()

        val repository = ProductoRepository(remoteDataSource, productoDao)

        //When
        val result = repository.getProducto(5)

        //Then
        assertEquals(productoDto, result)
    }

    @Test
    fun deleteProducto() = runTest {
        //Given
        val productoDto = ProductoDto(
            productoId = 5,
            nombre = "Leche",
            descripcion = "Leche entera",
            categoriaId = 1,
            proveedorId = 1,
            precio = 2.5f
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val productoDao = mockk<ProductoDao>()

        val repository = ProductoRepository(remoteDataSource, productoDao)

        //When
        val result = repository.deleteProducto(5)

        //Then
        assertEquals(productoDto, result)
    }

    @Test
    fun updateProducto() = runTest {
        //Given
        val productoDto = ProductoDto(
            productoId = 5,
            nombre = "Leche",
            descripcion = "Leche entera",
            categoriaId = 1,
            proveedorId = 1,
            precio = 2.5f
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val productoDao = mockk<ProductoDao>()

        val repository = ProductoRepository(remoteDataSource, productoDao)

        //When
        val result = repository.updateProducto(5, productoDto)

        //Then
        assertEquals(productoDto, result)
    }

    @Test
    fun getProductos() = runTest {
        //Given
        val productos = listOf(
            ProductoDto(
                productoId = 5,
                nombre = "Leche",
                descripcion = "Leche entera",
                categoriaId = 1,
                proveedorId = 1,
                precio = 2.5f
            )
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val productoDao = mockk<ProductoDao>()

        val repository = ProductoRepository(remoteDataSource, productoDao)

        //When
        val result = repository.getProductos()

        //Then
        assertEquals(productos, result)
    }
}