package com.actrabajoequipo.recipesapp.model

import com.actrabajoequipo.recipesapp.model.ApiBook

class RecipesRepository {

    suspend fun getRecipes() =
        ApiBook.SERVICE
            .getRecipes()
}