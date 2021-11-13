package com.actrabajoequipo.recipesapp.ui.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.model.ManageFireBase
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.model.database.Recipe
import com.actrabajoequipo.recipesapp.ui.Scope
import com.actrabajoequipo.recipesapp.ui.common.Event
import kotlinx.coroutines.launch

class UserProfileViewModel (private val recipesRepository: RecipesRepository) : ViewModel(),
    Scope by Scope.Impl() {

    private val _uiModelMyRecipes = MutableLiveData<UIModelMyRecipes>()
    val uiModelMyRecipes: LiveData<UIModelMyRecipes>
        get() {
            if (_uiModelMyRecipes.value == null) refresh()
            return _uiModelMyRecipes
        }

    private val _uiModelMyFavRecipes = MutableLiveData<UIModelMyFavRecipes>()
    val uiModelMyFavRecipes: LiveData<UIModelMyFavRecipes>
        get() {
            if (_uiModelMyFavRecipes.value == null) refresh()
            return _uiModelMyFavRecipes
        }

    private val _navigation = MutableLiveData<Event<Recipe>>()
    val navigation: LiveData<Event<Recipe>> = _navigation

    sealed class UIModelMyRecipes {
        object Loading : UIModelMyRecipes()
        class ContentMyRecipes(val myRecipes: List<Recipe>) : UIModelMyRecipes()
    }
    sealed class UIModelMyFavRecipes {
        object Loading : UIModelMyFavRecipes()
        class ContentMyFavourites(val myFavRecipes: List<Recipe>) : UIModelMyFavRecipes()
    }
    init {
        initScope()
    }

    fun refresh() {
        launch {
            _uiModelMyRecipes.value = UIModelMyRecipes.Loading
            _uiModelMyRecipes.value = ManageFireBase.returnUserUID()?.let { userUID ->
                recipesRepository.findByUserUID(
                    userUID
                )
            }?.let { recipeList ->
                UIModelMyRecipes.ContentMyRecipes(recipeList) }

            _uiModelMyFavRecipes.value = UIModelMyFavRecipes.Loading
            _uiModelMyFavRecipes.value = UIModelMyFavRecipes.ContentMyFavourites(recipesRepository.findByFavourites(true))
        }
    }

    fun onRecipeClicked(recipe: Recipe) {
        _navigation.value = Event(recipe)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}