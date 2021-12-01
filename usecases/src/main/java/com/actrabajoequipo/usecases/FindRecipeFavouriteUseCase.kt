package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.RecipeRepository
import com.actrabajoequipo.domain.Recipe

class FindRecipeFavouriteUseCase(private val recipeRepository: RecipeRepository) {
    suspend fun invoke(isFav: Boolean): List<Recipe> = recipeRepository.findRecipeByFavourites(isFav)
}