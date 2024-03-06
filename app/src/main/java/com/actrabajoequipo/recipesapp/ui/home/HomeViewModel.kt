package com.actrabajoequipo.recipesapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.recipesapp.data.NetworkManager
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import com.actrabajoequipo.recipesapp.ui.common.Event
import com.actrabajoequipo.usecases.GetRecipesUseCase
import com.actrabajoequipo.usecases.SearchRecipeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val searchRecipeUseCase: SearchRecipeUseCase,
    private val networkManager: NetworkManager
) : ScopedViewModel() {

    private val _uiModel = MutableLiveData<UIModel>()
    val uiModel: LiveData<UIModel>
        get() {
            refresh()
            return _uiModel
        }

    private val _navigation = MutableLiveData<Event<Recipe>>()
    val navigation: LiveData<Event<Recipe>> = _navigation

    sealed class UIModel {
        object Loading : UIModel()
        data class Content(val recipes: List<Recipe>) : UIModel()
    }

    init {
        initScope()
    }

    fun refresh() {
        val fromRemote = networkManager.isNetworkAvailable()
        launch {
            _uiModel.value = UIModel.Loading
            _uiModel.value = UIModel.Content(getRecipesUseCase.invoke(fromRemote))
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
            _uiModel.value = UIModel.Content(searchRecipeUseCase.invoke(query))
        }
    }
}