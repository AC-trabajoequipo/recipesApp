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

    private val _uiModel = MutableLiveData<UIModel>()
    val uiModel: LiveData<UIModel>
        get() {
            if (_uiModel.value == null) findRecipe()
            return _uiModel
        }

    sealed class UIModel {
        object Loading : UIModel()
        class Content(val recipe: Recipe) : UIModel()
    }

    init {
        initScope()
    }

    fun findRecipe() = launch {
        _uiModel.value = UIModel.Content(recipesRepository.findById(recipeId))
    }
}