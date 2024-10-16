package edu.ucne.registrocompras.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.registrocompras.data.local.dao.ProveedorDao
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ProveedorDto
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.*
import kotlinx.coroutines.test.runTest

import org.junit.Test
import retrofit2.Response

class ProveedorRepositoryTest {

    @Test
    fun addProveedor() = runTest {
        //Given

        val proveedorDto = ProveedorDto(
            proveedorId = 15,
            nombre = "David",
            rnc = "40234573653",
            direccion = "Pimentel",
            email = "david123@gmail.com",
            fechaCreacion = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val proveedorDao = mockk<ProveedorDao>()

        val repository = ProveedorRepository(remoteDataSource, proveedorDao)

        coEvery { remoteDataSource.addProveedor(proveedorDto) } returns proveedorDto

        //When
        val result = repository.addProveedor(proveedorDto)

        //Then
        assertEquals(proveedorDto, result)
    }

    @Test
    fun getProveedor() = runTest {
        //Given
        val proveedor = ProveedorDto(
            proveedorId = 15,
            nombre = "David",
            rnc = "40234573653",
            direccion = "Pimentel",
            email = "david123@gmail.com",
            fechaCreacion = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val proveedorDao = mockk<ProveedorDao>()

        val repository = ProveedorRepository(remoteDataSource, proveedorDao)

        coEvery { remoteDataSource.getProveedor(15) } returns proveedor

        //When
        val result = repository.getProveedor(15)

        //Then
        assertEquals(proveedor, result)
    }

    @Test
    fun deleteProveedor() = runTest {
        //Given

        val proveedorDto = ProveedorDto(
            proveedorId = 15,
            nombre = "David",
            rnc = "40234573653",
            direccion = "Pimentel",
            email = "david123@gmail.com",
            fechaCreacion = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val proveedorDao = mockk<ProveedorDao>()

        val repository = ProveedorRepository(remoteDataSource, proveedorDao)

        //coEvery { remoteDataSource.deleteProveedor(15) } returns proveedorDto

        //When
        val result = repository.deleteProveedor(15)

        //Then
        assertEquals(proveedorDto, result)

    }

    @Test
    fun updateProveedor() = runTest{
        //Given
        val proveedorDto = ProveedorDto(
            proveedorId = 15,
            nombre = "David",
            rnc = "40234573653",
            direccion = "Pimentel",
            email = "david123@gmail.com",
            fechaCreacion = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val proveedorDao = mockk<ProveedorDao>()

        val repository = ProveedorRepository(remoteDataSource, proveedorDao)

//        coEvery { remoteDataSource.updateProveedor(15, proveedorDto) } returns proveedorDto

        //When
        val result = repository.updateProveedor(15, proveedorDto)

        //Then
        assertEquals(proveedorDto, result)
    }

    @Test
    fun getProveedores() = runTest {
        // Given
        val proveedores = listOf(
            ProveedorDto(
                 1,
             "2024-10-14T03:06:27",
         "David",
        "40234573653",
         "Pimentel",
        "david123@gmail.com"
            )
        )
        val remoteDataSource = mockk<RemoteDataSource>()
        val proveedorDao = mockk<ProveedorDao>()

        val repository = ProveedorRepository(remoteDataSource, proveedorDao)

        coEvery { remoteDataSource.getProveedores() } returns proveedores

        // When
        repository.getProveedores().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(proveedores)

            cancelAndIgnoreRemainingEvents()
        }
    }
}