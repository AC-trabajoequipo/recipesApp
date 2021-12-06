package com.actrabajoequipo.recipesapp.ui.addrecipe

import com.actrabajoequipo.recipesapp.server.FirebaseManager
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
class AddRecipeModule {
    @Provides
    fun addRecipeViewModelProvider(
        firebaseManager: FirebaseManager,
        coroutineDispatcher: CoroutineDispatcher
    ): AddRecipeViewModel {
        return AddRecipeViewModel(firebaseManager, coroutineDispatcher)
    }
}

@Subcomponent(modules = [(AddRecipeModule::class)])
interface AddRecipeComponent {
    val addRecipeViewModel: AddRecipeViewModel
}