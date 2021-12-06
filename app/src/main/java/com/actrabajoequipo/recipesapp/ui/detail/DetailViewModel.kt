package com.actrabajoequipo.recipesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import com.actrabajoequipo.usecases.FindRecipeByIdUseCase
import com.actrabajoequipo.usecases.UpdateRecipeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val recipeId: String,
    private val findRecipeByIdUseCase: FindRecipeByIdUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> get() = _recipe

    init {
        initScope()
    }

    fun findRecipe() = launch {
        _recipe.value = findRecipeByIdUseCase.invoke(recipeId)
    }

    fun onFavoriteClicked() {
        launch {
            recipe.value?.let {
                val updateRecipe = it.copy(favorite = !it.favorite)
                _recipe.value = updateRecipe
                updateRecipeUseCase.invoke(updateRecipe)
            }
        }
    }
}