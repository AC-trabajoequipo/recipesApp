package com.actrabajoequipo.recipesapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.model.database.Recipe
import com.actrabajoequipo.recipesapp.ui.Scope
import com.actrabajoequipo.recipesapp.ui.common.Event
import kotlinx.coroutines.launch

class HomeViewModel(private val recipesRepository: RecipesRepository) : ViewModel(),
    Scope by Scope.Impl() {

    private val _uiModel = MutableLiveData<UIModel>()
    val uiModel: LiveData<UIModel>
        get() {
            if (_uiModel.value == null) refresh()
            return _uiModel
        }

    private val _navigation = MutableLiveData<Event<Recipe>>()
    val navigation: LiveData<Event<Recipe>> = _navigation

    sealed class UIModel {
        object Loading : UIModel()
        class Content(val recipes: List<Recipe>) : UIModel()
    }

    init {
        initScope()
    }

    fun refresh() {
        launch {
            _uiModel.value = UIModel.Loading
            _uiModel.value = UIModel.Content(recipesRepository.getRecipes())
        }
    }

    fun onRecipeClicked(recipe: Recipe) {
        _navigation.value = Event(recipe)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun doSearch(query: String) {
        launch {
            _uiModel.value = UIModel.Content(recipesRepository.search(query))
        }
    }
}