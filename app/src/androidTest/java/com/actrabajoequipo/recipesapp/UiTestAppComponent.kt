package com.actrabajoequipo.recipesapp

import android.app.Application
import com.actrabajoequipo.recipesapp.data.server.ApiBook
import com.actrabajoequipo.recipesapp.di.AppComponent
import com.actrabajoequipo.recipesapp.di.AppModule
import com.actrabajoequipo.recipesapp.di.DataModule
import dagger.BindsInstance
import dagger.Component
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, TestServerModule::class])
interface UiTestAppComponent : AppComponent {

    //val apiBook: ApiBook
    //val mockWebServer: MockWebServer

    @Component.Factory
    interface FactoryUiTest {
        fun create(@BindsInstance app: Application): UiTestAppComponent
    }
}