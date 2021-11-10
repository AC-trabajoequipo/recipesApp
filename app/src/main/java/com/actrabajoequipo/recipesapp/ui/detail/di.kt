package com.actrabajoequipo.recipesapp.ui.detail

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.usecases.FindRecipeByIdUseCase
import com.actrabajoequipo.usecases.UpdateRecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailModule(private val recipeId: String) {

    @Provides
    fun detailViewModelProvider(
        findRecipeByIdUseCase: FindRecipeByIdUseCase,
        updateRecipeUseCase: UpdateRecipeUseCase
    ) = DetailViewModel(recipeId, findRecipeByIdUseCase, updateRecipeUseCase)

    @Provides
    fun findRecipeByIdUseCaseProvider(recipesRepository: RecipesRepository) =
        FindRecipeByIdUseCase(recipesRepository)

    @Provides
    fun updateRecipeUseCaseProvider(recipesRepository: RecipesRepository) =
        UpdateRecipeUseCase(recipesRepository)
}

@Subcomponent(modules = [(DetailModule::class)])
interface DetailComponent {
    val detailViewModel: DetailViewModel
}