package edu.ucne.registrocompras.data.repository

import edu.ucne.registrocompras.data.local.dao.ClienteDao
import edu.ucne.registrocompras.data.local.dao.ProveedorDao
import edu.ucne.registrocompras.data.remote.RemoteDataSource
import edu.ucne.registrocompras.data.remote.dto.ClienteDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test

class ClienteRepositoryTest {

    @Test
    fun addCliente() = runTest {
        //Given
        val clienteDto = ClienteDto(
            clienteId = 15,
            nombre = "David",
            telefono = "40234573653",
            direccion = "Pimentel",
            cedula = "david123@gmail.com",
            fechaCreacion = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val clienteDao = mockk<ClienteDao>()

        val repository = ClienteRepository(remoteDataSource, clienteDao)

        coEvery { remoteDataSource.addCliente(clienteDto) } returns clienteDto

        //When
        val result = repository.addCliente(clienteDto)

        //Then
        assertEquals(clienteDto, result)
    }

    @Test
    fun getCliente() = runTest{
        //Given
        val clienteDto = ClienteDto(
            clienteId = 1,
            nombre = "David",
            telefono = "40234573653",
            direccion = "Pimentel",
            cedula = "david123@gmail.com",
            fechaCreacion = "2024-10-14T03:0"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val clienteDao = mockk<ClienteDao>()

        val repository = ClienteRepository(remoteDataSource, clienteDao)

        coEvery { remoteDataSource.getCliente(1) } returns clienteDto

        //When
        val result = repository.getCliente(1)

        //Then
        assertEquals(clienteDto, result)
    }

    @Test
    fun deleteCliente() = runTest{
        //Given
        val clienteDto = ClienteDto(
            clienteId = 5,
            nombre = "David",
            telefono = "40234573653",
            direccion = "Pimentel",
            cedula = "david123@gmail.com",
            fechaCreacion = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val clienteDao = mockk<ClienteDao>()

        val repository = ClienteRepository(remoteDataSource, clienteDao)

       //coEvery { remoteDataSource.deleteCliente(5) } returns clienteDto

        //When
        val result = repository.deleteCliente(5)

        //Then
        assertEquals(clienteDto, result)
    }

    @Test
    fun updateCliente() = runTest {
        //Given
        val clienteDto = ClienteDto(
            clienteId = 5,
            nombre = "David",
            telefono = "40234573653",
            direccion = "Pimentel",
            cedula = "david123@gmail.com",
            fechaCreacion = "2024-10-14T03:06:27"
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val clienteDao = mockk<ClienteDao>()

        val repository = ClienteRepository(remoteDataSource, clienteDao)

//        coEvery { remoteDataSource.updateCliente(5, clienteDto) } returns clienteDto

        //When
        val result = repository.updateCliente(5, clienteDto)

        //Then
        assertEquals(clienteDto, result)
    }

    @Test
    fun getClientes() = runTest {
        //Given
        val clientes = listOf(
            ClienteDto(
                clienteId = 5,
                nombre = "David",
                telefono = "40234573653",
                direccion = "Pimentel",
                cedula = "david123@gmail.com",
                fechaCreacion = "2024-10-14T03:06:27"
            )
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val clienteDao = mockk<ClienteDao>()

        val repository = ClienteRepository(remoteDataSource, clienteDao)

        coEvery { remoteDataSource.getClientes() } returns clientes

        //When
        val result = repository.getClientes()

        //Then
        assertEquals(clientes, result)

    }
}