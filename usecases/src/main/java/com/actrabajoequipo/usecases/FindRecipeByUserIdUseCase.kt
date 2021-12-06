package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.domain.Recipe

class FindRecipeByUserIdUseCase(private val recipesRepository: RecipesRepository) {
    suspend fun invoke(uid: String): List<Recipe> = recipesRepository.findRecipeByUserUID(uid)
}