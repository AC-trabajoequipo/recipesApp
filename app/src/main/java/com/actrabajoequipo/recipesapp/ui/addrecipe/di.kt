package com.actrabajoequipo.recipesapp.ui.addrecipe

import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class AddRecipeModule {
    @Provides
    fun addRecipeViewModelProvider(
        firebaseManager: FirebaseManager
    ): AddRecipeViewModel {
        return AddRecipeViewModel(firebaseManager)
    }
}

@Subcomponent(modules = [(AddRecipeModule::class)])
interface AddRecipeComponent {
    val addRecipeViewModel: AddRecipeViewModel
}