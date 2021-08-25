package com.actrabajoequipo.recipesapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.model.RecipeDto
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(), Scope by Scope.Impl() {

    private val recipesRepository: RecipesRepository by lazy { RecipesRepository() }

    private val _recipes = MutableLiveData<List<RecipeDto>>()
    val recipes: LiveData<List<RecipeDto>>
        get() {
            if (_recipes.value == null) refresh()
            return _recipes
        }

    private fun refresh() {
        launch {
            _recipes.value = recipesRepository.getRecipes()
        }
    }

    init {
        initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun onRecipeClicked(recipe: RecipeDto) {
        Log.d("onRecipeClicked", "Recipe ${recipe.id} was clicked")
    }
}