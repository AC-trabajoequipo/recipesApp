package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.domain.Recipe

class GetRecipesUseCase(private val recipesRepository: RecipesRepository) {
    suspend fun invoke(): List<Recipe> = recipesRepository.getRecipes()
}