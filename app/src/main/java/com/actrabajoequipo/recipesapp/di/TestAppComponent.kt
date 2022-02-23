package com.actrabajoequipo.recipesapp.di

import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.data.source.RemoteDataSource
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class, DataModule::class])
interface TestAppComponent: AppComponent {

    val localDataSource: LocalDataSource
    val remoteDataSource: RemoteDataSource

    @Component.Factory
    interface FactoryTest {
        fun create(): TestAppComponent
    }
}