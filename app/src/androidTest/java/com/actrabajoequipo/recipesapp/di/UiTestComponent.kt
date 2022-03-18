package com.actrabajoequipo.recipesapp.di

import android.app.Application
import com.actrabajoequipo.recipesapp.data.server.ApiBook
import dagger.BindsInstance
import dagger.Component
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, TestServerModule::class])
interface UiTestComponent: AppComponent {

    val apiBook: ApiBook
    val mockWebServer: MockWebServer

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): UiTestComponent
    }
}