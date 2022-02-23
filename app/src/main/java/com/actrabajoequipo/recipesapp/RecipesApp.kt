package com.actrabajoequipo.recipesapp

import android.app.Application
import com.actrabajoequipo.recipesapp.di.AppComponent
import com.actrabajoequipo.recipesapp.di.DaggerAppComponent

open class RecipesApp : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = initRecipesComponent()
    }

    open fun initRecipesComponent() = DaggerAppComponent
        .factory()
        .create(this)
}