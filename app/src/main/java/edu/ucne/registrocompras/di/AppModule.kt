package edu.ucne.registrocompras.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrocompras.data.local.database.ComprasDb
import edu.ucne.registrocompras.data.remote.ComprasApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule{
    const val BASE_URL = "https://comprasapi-acaudcd0hcf2ehaz.eastus2-01.azurewebsites.net/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesComprasApi(moshi: Moshi): ComprasApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ComprasApi::class.java)
    }

    @Provides
    @Singleton
    fun providesComprasDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            ComprasDb::class.java,
            "Compras.DB"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesProveedorDao(compraDb: ComprasDb) = compraDb.proveedorDao()

    @Provides
    @Singleton
    fun providesClienteDao(compraDb: ComprasDb) = compraDb.clienteDao()

    @Provides
    @Singleton
    fun providesCategoriaDao(compraDb: ComprasDb) = compraDb.categoriaDao()

    @Provides
    @Singleton
    fun providesProductoDao(compraDb: ComprasDb) = compraDb.productoDao()

    @Provides
    @Singleton
    fun providesCompraDao(compraDb: ComprasDb) = compraDb.compraDao()
}