package edu.ucne.registrocompras.data.remote

import edu.ucne.registrocompras.data.remote.dto.CategoriaDto
import edu.ucne.registrocompras.data.remote.dto.ClienteDto
import edu.ucne.registrocompras.data.remote.dto.CompraDto
import edu.ucne.registrocompras.data.remote.dto.ProductoDto
import edu.ucne.registrocompras.data.remote.dto.ProveedorDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ComprasApi {
    @POST("api/Proveedores")
    suspend fun addProveedor(@Body proveedorDto: ProveedorDto?): ProveedorDto

    @GET("api/Proveedores/{proveedorId}")
    suspend fun getProveedor(@Path("proveedorId") proveedorId: Int): ProveedorDto

    @DELETE("api/Proveedores/{proveedorId}")
    suspend fun deleteProveedor(@Path("proveedorId") proveedorId: Int)

    @PUT("api/Proveedores/{proveedorId}")
    suspend fun updateProveedor(@Path("proveedorId") proveedorId: Int)

    @GET("api/Proveedores")
    suspend fun getProveedores(): List<ProveedorDto>


    @POST("api/Clientes")
    suspend fun addCliente(@Body clienteDto: ClienteDto?): ClienteDto

    @GET("api/Clientes/{clienteId}")
    suspend fun getCliente(@Path("clienteId") clienteId: Int): ClienteDto

    @DELETE("api/Clientes/{clienteId}")
    suspend fun deleteCliente(@Path("clienteId") clienteId: Int)

    @PUT("api/Clientes/{clienteId}")
    suspend fun updateCliente(@Path("clienteId") clienteId: Int)

    @GET("api/Clientes")
    suspend fun getClientes(): List<ClienteDto>


    @POST("api/Categorias")
    suspend fun addCategoria(@Body categoriaDto: CategoriaDto?): CategoriaDto

    @GET("api/Categorias/{categoriaId}")
    suspend fun getCategoria(@Path("categoriaId") categoriaId: Int): CategoriaDto

    @DELETE("api/Categorias/{categoriaId}")
    suspend fun deleteCategoria(@Path("categoriaId") categoriaId: Int)

    @PUT("api/Categorias/{categoriaId}")
    suspend fun updateCategoria(@Path("categoriaId") categoriaId: Int)

    @GET("api/Categorias")
    suspend fun getCategorias(): List<CategoriaDto>


    @POST("api/Productos")
    suspend fun addProducto(@Body productoDto: ProductoDto?): ProductoDto

    @GET("api/Productos/{productoId}")
    suspend fun getProducto(@Path("productoId") productoId: Int): ProductoDto

    @DELETE("api/Productos/{productoId}")
    suspend fun deleteProducto(@Path("productoId") productoId: Int)

    @PUT("api/Productos/{productoId}")
    suspend fun updateProducto(@Path("productoId") productoId: Int)

    @GET("api/Productos")
    suspend fun getProductos(): List<ProductoDto>


    @POST("api/Compras")
    suspend fun addCompra(@Body compraDto: CompraDto?): CompraDto

    @GET("api/Compras/{compraId}")
    suspend fun getCompra(@Path("compraId") compraId: Int): CompraDto

    @DELETE("api/Compras/{compraId}")
    suspend fun deleteCompra(@Path("compraId") compraId: Int)

    @PUT("api/Compras/{compraId}")
    suspend fun updateCompra(@Path("compraId") compraId: Int)

    @GET("api/Compras")
    suspend fun getCompras(): List<CompraDto>
}