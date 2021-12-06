package com.actrabajoequipo.recipesapp.ui.home

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.usecases.GetRecipesUseCase
import com.actrabajoequipo.usecases.SearchRecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
class HomeModule {

    @Provides
    fun homeViewModelProvider(
        getRecipesUseCase: GetRecipesUseCase,
        searchRecipeUseCase: SearchRecipeUseCase,
        coroutineDispatcher: CoroutineDispatcher
    ) = HomeViewModel(getRecipesUseCase, searchRecipeUseCase, coroutineDispatcher)

    @Provides
    fun getRecipesUseCaseProvider(
        recipesRepository: RecipesRepository
    ) = GetRecipesUseCase(recipesRepository)

    @Provides
    fun searchRecipeUseCaseProvider(
        recipesRepository: RecipesRepository
    ) = SearchRecipeUseCase(recipesRepository)
}

@Subcomponent(modules = [(HomeModule::class)])
interface HomeComponent {
    val homeViewModel: HomeViewModel
}