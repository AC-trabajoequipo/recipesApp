package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.domain.Recipe

class UpdateRecipeUseCase(private val recipesRepository: RecipesRepository) {
    suspend fun invoke(recipe: Recipe) = with(recipe) {
        copy(favorite = !favorite).also {
            recipesRepository.update(it)
        }
    }
}