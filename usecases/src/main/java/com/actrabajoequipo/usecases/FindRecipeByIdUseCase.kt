package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.domain.Recipe

class FindRecipeByIdUseCase(private val recipesRepository: RecipesRepository) {
    suspend fun invoke(id: String): Recipe = recipesRepository.findById(id)
}