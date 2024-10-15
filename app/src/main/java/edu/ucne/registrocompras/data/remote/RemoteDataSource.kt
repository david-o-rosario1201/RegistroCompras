package edu.ucne.registrocompras.data.remote

import edu.ucne.registrocompras.data.remote.dto.CategoriaDto
import edu.ucne.registrocompras.data.remote.dto.ClienteDto
import edu.ucne.registrocompras.data.remote.dto.CompraDto
import edu.ucne.registrocompras.data.remote.dto.ProductoDto
import edu.ucne.registrocompras.data.remote.dto.ProveedorDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val comprasApi: ComprasApi
) {
    suspend fun addProveedor(proveedorDto: ProveedorDto) = comprasApi.addProveedor(proveedorDto)

    suspend fun getProveedor(proveedorId: Int) = comprasApi.getProveedor(proveedorId)

    suspend fun deleteProveedor(proveedorId: Int) = comprasApi.deleteProveedor(proveedorId)

    suspend fun updateProveedor(proveedorId: Int) = comprasApi.updateProveedor(proveedorId)

    suspend fun getProveedores() = comprasApi.getProveedores()


    suspend fun addCliente(clienteDto: ClienteDto) = comprasApi.addCliente(clienteDto)

    suspend fun getCliente(clienteId: Int) = comprasApi.getCliente(clienteId)

    suspend fun deleteCliente(clienteId: Int) = comprasApi.deleteCliente(clienteId)

    suspend fun updateCliente(clienteId: Int) = comprasApi.updateCliente(clienteId)

    suspend fun getClientes() = comprasApi.getClientes()


    suspend fun addCategoria(categoriaDto: CategoriaDto) = comprasApi.addCategoria(categoriaDto)

    suspend fun getCategoria(categoriaId: Int) = comprasApi.getCategoria(categoriaId)

    suspend fun deleteCategoria(categoriaId: Int) = comprasApi.deleteCategoria(categoriaId)

    suspend fun updateCategoria(categoriaId: Int) = comprasApi.updateCategoria(categoriaId)

    suspend fun getCategorias() = comprasApi.getCategorias()


    suspend fun addProducto(productoDto: ProductoDto) = comprasApi.addProducto(productoDto)

    suspend fun getProducto(productoId: Int) = comprasApi.getProducto(productoId)

    suspend fun deleteProducto(productoId: Int) = comprasApi.deleteProducto(productoId)

    suspend fun updateProducto(productoId: Int) = comprasApi.updateProducto(productoId)

    suspend fun getProductos() = comprasApi.getProductos()


    suspend fun addCompra(compraDto: CompraDto) = comprasApi.addCompra(compraDto)

    suspend fun getCompra(compraId: Int) = comprasApi.getCompra(compraId)

    suspend fun deleteCompra(compraId: Int) = comprasApi.deleteCompra(compraId)

    suspend fun updateCompra(compraId: Int) = comprasApi.updateCompra(compraId)

    suspend fun getCompras() = comprasApi.getCompras()
}