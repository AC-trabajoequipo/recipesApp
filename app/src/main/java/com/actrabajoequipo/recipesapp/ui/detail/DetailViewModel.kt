package com.actrabajoequipo.recipesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.model.database.Recipe
import com.actrabajoequipo.recipesapp.ui.Scope
import kotlinx.coroutines.launch

class DetailViewModel(
    private val recipeId: String,
    private val recipesRepository: RecipesRepository
) : ViewModel(),
    Scope by Scope.Impl() {

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> get() = _recipe

    init {
        initScope()
    }

    fun findRecipe() = launch {
        _recipe.value = recipesRepository.findById(recipeId)
    }

    fun onFavoriteClicked() {
        launch {
            recipe.value?.let {
                val updateRecipe = it.copy(favorite = !it.favorite)
                _recipe.value = updateRecipe
                recipesRepository.update(updateRecipe)
            }
        }
    }
}