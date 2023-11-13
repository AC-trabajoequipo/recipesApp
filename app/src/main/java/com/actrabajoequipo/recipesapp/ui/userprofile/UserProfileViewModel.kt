package com.actrabajoequipo.recipesapp.ui.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import com.actrabajoequipo.recipesapp.ui.common.Event
import com.actrabajoequipo.usecases.FindRecipeByUserIdUseCase
import com.actrabajoequipo.usecases.FindRecipeFavouriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel
@Inject constructor(
    private val firebaseManager: FirebaseManager,
    private val findRecipeFavouriteUseCase: FindRecipeFavouriteUseCase,
    private val findRecipeByUserIdUseCase: FindRecipeByUserIdUseCase
) : ScopedViewModel() {

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
            _uiModelMyRecipes.value = firebaseManager.returnUserUID()?.let { userUID ->
                findRecipeByUserIdUseCase.invoke(
                    userUID
                )
            }?.let { recipeList ->
                UIModelMyRecipes.ContentMyRecipes(recipeList)
            }

            _uiModelMyFavRecipes.value = UIModelMyFavRecipes.Loading
            _uiModelMyFavRecipes.value =
                UIModelMyFavRecipes.ContentMyFavourites(findRecipeFavouriteUseCase.invoke(true))
        }
    }

    fun onRecipeClicked(recipe: Recipe) {
        _navigation.value = Event(recipe)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun signOut() {
        firebaseManager.signOut()
    }

    fun isUserLoggedNotNull(): Boolean {
        return (firebaseManager.returnUserUID() != null)
    }

    fun getEmailUser(): String {
        return firebaseManager.getEmailUser()
    }

}