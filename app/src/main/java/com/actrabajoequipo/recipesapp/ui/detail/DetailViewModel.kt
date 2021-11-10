package com.actrabajoequipo.recipesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.recipesapp.ui.Scope
import com.actrabajoequipo.usecases.FindRecipeByIdUseCase
import com.actrabajoequipo.usecases.UpdateRecipeUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val recipeId: String,
    private val findRecipeByIdUseCase: FindRecipeByIdUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase
) : ViewModel(),
    Scope by Scope.Impl() {

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