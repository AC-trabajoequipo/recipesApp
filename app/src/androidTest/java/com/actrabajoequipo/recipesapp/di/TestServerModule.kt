package com.actrabajoequipo.recipesapp.di

import com.actrabajoequipo.recipesapp.data.server.ApiBook
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestServerModule {

    @Singleton
    @Provides
    @Named("baseUrl")
    fun baseUrlProvider():String = "http://127.0.0.1:8080"

    @Singleton
    @Provides
    fun mockWebServerProvider(): MockWebServer {
        var mockWebServer: MockWebServer? = null
        val thread = Thread {
            mockWebServer = MockWebServer()
            mockWebServer?.start(8080)
        }
        thread.start()
        thread.join()
        return mockWebServer ?: throw NullPointerException()
    }

    @Singleton
    @Provides
    fun apiServiceProvider(
        @Named("baseUrl") baseUrl: String
    ): ApiBook = ApiBook(baseUrl)
}