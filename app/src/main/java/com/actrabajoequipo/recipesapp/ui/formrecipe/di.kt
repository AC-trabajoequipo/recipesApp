package com.actrabajoequipo.recipesapp.ui.formrecipe

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.usecases.PostRecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class FormRecipeModule() {

    @Provides
    fun formRecipeViewModelProvider(
        postRecipeUseCase: PostRecipeUseCase
    ): FormRecipeViewModel {
        return FormRecipeViewModel(postRecipeUseCase)
    }

    @Provides
    fun postRecipeUseCaseProvider(recipesRepository: RecipesRepository) =
        PostRecipeUseCase(recipesRepository)
}

@Subcomponent
interface FormRecipeComponent {
    val formRecipeViewModel: FormRecipeViewModel
}