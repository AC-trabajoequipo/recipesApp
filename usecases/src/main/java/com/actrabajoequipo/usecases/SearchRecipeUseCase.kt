package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.RecipesRepository

class SearchRecipeUseCase(private val recipesRepository: RecipesRepository) {
    suspend fun invoke(query: String) = recipesRepository.search(query)
}