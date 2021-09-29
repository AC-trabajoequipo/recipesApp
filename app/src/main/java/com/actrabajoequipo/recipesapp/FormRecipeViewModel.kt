package com.actrabajoequipo.recipesapp

import android.widget.Toast
import androidx.lifecycle.*
import com.actrabajoequipo.recipesapp.model.FireBaseRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import kotlinx.coroutines.launch

class FormRecipeViewModel() : ViewModel(), Scope by Scope.Impl() {

    sealed class ValidatedFields() {
        class EmptyFieldsError : ValidatedFields()
        class EmptyPhotoFieldError : ValidatedFields()
        class EmptyIdFieldError : ValidatedFields()
        class FormValidated : ValidatedFields()
    }

    sealed class SaveImage() {
        class Success : SaveImage()
        class Error : SaveImage()
    }

    init {
        initScope()
    }

    private val _formState = MutableLiveData<ValidatedFields>()
    val formState: LiveData<ValidatedFields>
        get() {
            return _formState
        }

    private val _imageState = MutableLiveData<SaveImage>()
    val imageState: LiveData<SaveImage>
        get() {
            return _imageState
        }

    fun validatedFields(photoUrl: String?, id: String?, ingredients: ArrayList<String>) {
        launch {
            if (photoUrl == null) {
                _formState.postValue(ValidatedFields.EmptyPhotoFieldError())
            } else if (id == null) {
                _formState.postValue(ValidatedFields.EmptyIdFieldError())
            } else if (!editTextArrayListValidated(ingredients))
                _formState.postValue(ValidatedFields.EmptyFieldsError())
            else _formState.postValue(ValidatedFields.FormValidated())
        }
    }

    fun saveImage(
        user: String,
        id: String,
        photoUrl: String,
        titleRecipe: String,
        ingredients: ArrayList<String>,
        stepRecipe: String
    ) {
        launch {
            var recipe = Recipe(id, photoUrl, titleRecipe, ingredients, stepRecipe)
            if(FireBaseRepository.uploadRecipe(user = user,recipe = recipe)){
                _imageState.postValue(SaveImage.Success())
            }else{
                _imageState.postValue(SaveImage.Error())
            }
    }
}

private fun editTextArrayListValidated(ingredients: ArrayList<String>): Boolean {
    var isValid = true
    for (ingredient in ingredients) {
        if (ingredient.isEmpty()) {
            isValid = false
        }
    }
    return isValid
}
}

@Suppress("UNCHECKED_CAST")
class FormRecipeViewModelFactory() :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FormRecipeViewModel() as T
    }
}