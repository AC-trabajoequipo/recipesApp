package com.actrabajoequipo.recipesapp

import android.app.Application
import com.actrabajoequipo.recipesapp.di.AppComponent
import com.actrabajoequipo.recipesapp.di.DaggerAppComponent

class RecipesApp : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .factory()
            .create(this)
    }
}