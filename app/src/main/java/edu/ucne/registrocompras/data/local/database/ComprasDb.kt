package edu.ucne.registrocompras.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrocompras.data.local.dao.CategoriaDao
import edu.ucne.registrocompras.data.local.dao.ClienteDao
import edu.ucne.registrocompras.data.local.dao.CompraDao
import edu.ucne.registrocompras.data.local.dao.ProductoDao
import edu.ucne.registrocompras.data.local.dao.ProveedorDao
import edu.ucne.registrocompras.data.local.entities.ProveedorEntity

@Database(
    entities = [ProveedorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ComprasDb: RoomDatabase(){
    abstract fun proveedorDao(): ProveedorDao
    abstract fun clienteDao(): ClienteDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun productoDao(): ProductoDao
    abstract fun compraDao(): CompraDao
}