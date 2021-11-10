package com.actrabajoequipo.recipesapp.di

import android.app.Application
import androidx.room.Room
import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.recipesapp.data.database.RecipeDatabase
import com.actrabajoequipo.recipesapp.data.database.RoomDataSource
import com.actrabajoequipo.recipesapp.server.RecipesDataSource
import dagger.Module
import dagger.Provides
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
    fun remoteDataSourceProvider(): RemoteDataSource = RecipesDataSource()
}