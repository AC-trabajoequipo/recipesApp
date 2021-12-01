package com.actrabajoequipo.usecases



import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.domain.Recipe

class FindRecipeFavouriteUseCase(private val recipeRepository: RecipesRepository) {
    suspend fun invoke(isFav: Boolean): List<Recipe> = recipeRepository.findRecipeByFavourites(isFav)
}