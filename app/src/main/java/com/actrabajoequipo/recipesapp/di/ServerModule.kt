package com.actrabajoequipo.recipesapp.di

import com.actrabajoequipo.recipesapp.data.server.ApiBook
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServerModule {

    @Singleton
    @Provides
    @Named("baseUrl")
    fun baseUrlProvider() = "https://recipesapp-ac-default-rtdb.europe-west1.firebasedatabase.app/"

    @Singleton
    @Provides
    fun apiServiceProvider(
        @Named("baseUrl") baseUrl: String
    ) = ApiBook(baseUrl)

}