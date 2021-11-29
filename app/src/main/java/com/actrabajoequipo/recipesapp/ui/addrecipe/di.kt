package com.actrabajoequipo.recipesapp.ui.addrecipe

import com.actrabajoequipo.recipesapp.server.FirebaseManager

@Module
class AddRecipeModule{
    @Provides
    fun addRecipeViewModelProvider(firebaseManager: FirebaseManager) : AddRecipeViewModel {
        return AddRecipeViewModel(firebaseManager)
    }
}

@Subcomponent(modules = [(AddRecipeModule::class)])
interface AddRecipeComponent {
    val addRecipeViewModel: AddRecipeViewModel
}