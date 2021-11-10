package com.actrabajoequipo.recipesapp.di

import android.app.Application
import com.actrabajoequipo.recipesapp.ui.detail.DetailComponent
import com.actrabajoequipo.recipesapp.ui.detail.DetailModule
import com.actrabajoequipo.recipesapp.ui.formrecipe.FormRecipeComponent
import com.actrabajoequipo.recipesapp.ui.formrecipe.FormRecipeModule
import com.actrabajoequipo.recipesapp.ui.home.HomeComponent
import com.actrabajoequipo.recipesapp.ui.home.HomeModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    fun plus(module: HomeModule): HomeComponent
    fun plus(module: DetailModule): DetailComponent
    fun plus(module: FormRecipeModule): FormRecipeComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}