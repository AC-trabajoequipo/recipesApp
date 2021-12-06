package com.actrabajoequipo.recipesapp.di

import android.app.Application
import androidx.room.Room
import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.recipesapp.data.database.RecipeDatabase
import com.actrabajoequipo.recipesapp.data.database.RoomDataSource
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.server.ServerDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application): RecipeDatabase = Room.databaseBuilder(
        app,
        RecipeDatabase::class.java, "recipe-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: RecipeDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = ServerDataSource()

    @Provides
    @Singleton
    fun firebaseManagerProvider() = FirebaseManager()

    @Provides
    @Singleton
    fun dispatcherProvider(): CoroutineDispatcher = Dispatchers.Main
}