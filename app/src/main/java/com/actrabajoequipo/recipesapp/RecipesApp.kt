package com.actrabajoequipo.recipesapp

import android.app.Application
import androidx.room.Room
import com.actrabajoequipo.recipesapp.model.database.RecipeDatabase

class RecipesApp : Application() {

    lateinit var db: RecipeDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            RecipeDatabase::class.java, "recipe-db"
        ).build()
    }
}