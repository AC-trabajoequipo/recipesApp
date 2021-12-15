package com.actrabajoequipo.recipesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import com.actrabajoequipo.usecases.FindRecipeByIdUseCase
import com.actrabajoequipo.usecases.UpdateRecipeUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val recipeId: String,
    private val findRecipeByIdUseCase: FindRecipeByIdUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase
) : ScopedViewModel() {

    data class UiModel(val recipe: Recipe)

    private val _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel>
        get() {
            if (_uiModel.value == null) findRecipe()
            return _uiModel
        }

    init {
        initScope()
    }

    fun findRecipe() = launch {
        _uiModel.value = UiModel(findRecipeByIdUseCase.invoke(recipeId))
    }

    fun onFavoriteClicked() {
        launch {
            uiModel.value?.recipe?.let {
                _uiModel.value = UiModel(updateRecipeUseCase.invoke(it))
            }
        }
    }
}