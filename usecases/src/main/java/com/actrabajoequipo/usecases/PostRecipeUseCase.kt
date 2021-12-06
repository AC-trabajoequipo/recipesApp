package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.domain.Recipe

class PostRecipeUseCase(private val recipesRepository: RecipesRepository) {
    suspend fun invoke(recipe: Recipe) = recipesRepository.postRecipe(recipe)
}