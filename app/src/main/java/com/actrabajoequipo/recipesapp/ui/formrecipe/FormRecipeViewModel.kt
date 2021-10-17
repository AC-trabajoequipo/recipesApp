package com.actrabajoequipo.recipesapp.ui.formrecipe

import android.net.Uri
import androidx.lifecycle.*
import com.actrabajoequipo.recipesapp.model.ManageFireBase
import com.actrabajoequipo.recipesapp.model.ManageFireBase.PhotoCallBack
import com.actrabajoequipo.recipesapp.model.RecipeDto
import com.actrabajoequipo.recipesapp.model.RecipeRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import kotlinx.coroutines.launch

class FormRecipeViewModel : ViewModel(), PhotoCallBack, Scope by Scope.Impl() {

    private val recipeRepository: RecipeRepository by lazy { RecipeRepository() }

    private var photoUrl: String? = null
    private var id: String? = null
    private var ingredientsWithoutEmpties = ArrayList<String>()

    sealed class ValidatedFields() {
        class EmptyTitleRecipeError : ValidatedFields()
        class EmptyDescriptionRecipeError : ValidatedFields()
        class EmptyIngredientsError : ValidatedFields()
        class EmptyPhotoFieldError : ValidatedFields()
        class EmptyIdFieldError : ValidatedFields()
        class FormValidated : ValidatedFields()
    }

    sealed class SaveRecipe() {
        class Success : SaveRecipe()
        class Error : SaveRecipe()
    }

    sealed class ImageUpload() {
        class Success : ImageUpload()
        class InProgress : ImageUpload()
        class Error : ImageUpload()
    }

    init {
        initScope()
    }

    private val _formState = MutableLiveData<ValidatedFields>()
    val formState: LiveData<ValidatedFields> get() = _formState

    private val _recipeState = MutableLiveData<SaveRecipe>()
    val recipeState: LiveData<SaveRecipe> get() = _recipeState

    private val _imageUpload = MutableLiveData<ImageUpload>()
    val imageUpload: LiveData<ImageUpload> get() = _imageUpload

    private var _progressUploadImage = MutableLiveData<Int>()
    val progressUploadImage: LiveData<Int> get() = _progressUploadImage


    fun validatedFields(
        titleRecipe: String?,
        descriptionRecipe: String?,
        ingredients: ArrayList<String>
    ) {
        launch {
            if (photoUrl == null) {
                _formState.postValue(ValidatedFields.EmptyPhotoFieldError())
            } else if (titleRecipe == null) {
                _formState.postValue(ValidatedFields.EmptyTitleRecipeError())
            } else if (descriptionRecipe == null) {
                _formState.postValue(ValidatedFields.EmptyDescriptionRecipeError())
            } else if (!editTextArrayListValidated(ingredients)) {
                _formState.postValue(ValidatedFields.EmptyIngredientsError())
            } else _formState.postValue(ValidatedFields.FormValidated())
        }
    }

    fun saveRecipe(
        idUser: String,
        titleRecipe: String,
        descriptionRecipe: String,
        stepRecipe: String
    ) {
        launch {

            var responsePostRecipe = recipeRepository.postRecipe(RecipeDto(
                idUser = idUser,
                name = titleRecipe,
                description = descriptionRecipe,
                imgUrl = photoUrl,
                ingredients = ingredientsWithoutEmpties,
                preparation = stepRecipe
            ))
            if (responsePostRecipe.name != null)
                _recipeState.postValue(SaveRecipe.Success())
            else
                _recipeState.postValue(SaveRecipe.Error())
        }
    }

    private fun editTextArrayListValidated(ingredients: ArrayList<String>): Boolean {
        var isValid = false
        for (ingredient in ingredients) {
            if (ingredient.isNotEmpty()) {
                isValid = true
                ingredientsWithoutEmpties.add(ingredient)
            }
        }
        return isValid
    }

    fun uploadImage(imageUri: Uri?) {
        launch {
            if (imageUri != null) {
                id = ManageFireBase.returnIdKeyEntry()
                if (id != null)
                    ManageFireBase.uploadPhotoRecipe(id, imageUri, this@FormRecipeViewModel)
                else
                    _formState.postValue(ValidatedFields.EmptyIdFieldError())
            }
        }
    }

    override fun onProgress(progress: Int) {
        launch {
            _progressUploadImage.postValue(progress)
            _imageUpload.postValue(ImageUpload.InProgress())
        }
    }

    override fun onComplete() {
    }

    override fun onSuccess(imageURL: String) {
        launch {
            _imageUpload.postValue(ImageUpload.Success())
            photoUrl = imageURL
        }
    }

    override fun onFailure() {
        launch {
            _imageUpload.postValue(ImageUpload.Error())
        }
    }
}
