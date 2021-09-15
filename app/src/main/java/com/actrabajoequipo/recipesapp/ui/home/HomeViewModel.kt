package com.actrabajoequipo.recipesapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.model.RecipeDto
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.actrabajoequipo.recipesapp.ui.common.Event
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(), Scope by Scope.Impl() {

    private val recipesRepository: RecipesRepository by lazy { RecipesRepository() }

    private val _uiModel = MutableLiveData<UIModel>()
    val uiModel: LiveData<UIModel>
        get() {
            if (_uiModel.value == null) refresh()
            return _uiModel
        }

    private val _navigation = MutableLiveData<Event<RecipeDto>>()
    val navigation: LiveData<Event<RecipeDto>> = _navigation

    sealed class UIModel {
        object Loading : UIModel()
        class Content(val recipes: List<RecipeDto>) : UIModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        launch {
            _uiModel.value = UIModel.Loading
            _uiModel.value = UIModel.Content(recipesRepository.getRecipes())
        }
    }

    fun onRecipeClicked(recipe: RecipeDto) {
        _navigation.value = Event(recipe)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}