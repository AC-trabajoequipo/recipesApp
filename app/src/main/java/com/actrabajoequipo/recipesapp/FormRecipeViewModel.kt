package com.actrabajoequipo.recipesapp

import android.net.Uri
import androidx.lifecycle.*
import com.actrabajoequipo.recipesapp.model.ManageFireBase
import com.actrabajoequipo.recipesapp.model.ManageFireBase.PhotoCallBack
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

class FormRecipeViewModel() : ViewModel(), PhotoCallBack, Scope by Scope.Impl() {

    private var photoUrl: String? = null
    private var id: String? = null

    sealed class ValidatedFields() {
        class EmptyFieldsError : ValidatedFields()
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


    fun validatedFields(ingredients: ArrayList<String>) {
        launch {
            id = ManageFireBase.returnIdKeyEntry()
            if (photoUrl == null) {
                _formState.postValue(ValidatedFields.EmptyPhotoFieldError())
            } else if (id == null) {
                _formState.postValue(ValidatedFields.EmptyIdFieldError())
            } else if (!editTextArrayListValidated(ingredients))
                _formState.postValue(ValidatedFields.EmptyFieldsError())
            else _formState.postValue(ValidatedFields.FormValidated())
        }
    }

    fun saveRecipe(
        user: String,
        photoUrl: String,
        titleRecipe: String,
        ingredients: ArrayList<String>,
        stepRecipe: String
    ) {
        launch {
            var recipe = Recipe(id!!, photoUrl, titleRecipe, ingredients, stepRecipe)
            if (ManageFireBase.uploadRecipe(user = user, recipe = recipe)) {
                _recipeState.postValue(SaveRecipe.Success())
            } else {
                _recipeState.postValue(SaveRecipe.Error())
            }
        }
    }

    private fun editTextArrayListValidated(ingredients: ArrayList<String>): Boolean {
        for (ingredient in ingredients) {
            if (ingredient.isNotEmpty()) {
                return true
            }
        }
        return false
    }

    fun uploadImage(imageUri: Uri?) {
        launch {
            if (imageUri != null) {
                ManageFireBase.uploadPhotoRecipe(imageUri, this@FormRecipeViewModel)
            }
        }
    }

    override fun onProgress(progress: Int) {
        _progressUploadImage.postValue(progress.toInt())
        _imageUpload.postValue(ImageUpload.InProgress())
    }

    override fun onComplete() {
        _progressUploadImage.postValue(0)
    }

    override fun onSuccess(imageURL: String) {
        _imageUpload.postValue(ImageUpload.Success())
        photoUrl = imageURL

    }

    override fun onFailure() {
        _imageUpload.postValue(ImageUpload.Error())
    }
}