package com.actrabajoequipo.recipesapp.ui.formrecipe

import android.net.Uri
import androidx.lifecycle.*
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.domain.User
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.server.FirebaseManager.PhotoCallBack
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import com.actrabajoequipo.usecases.FindUserByIdUseCase
import com.actrabajoequipo.usecases.PatchUserUseCase
import com.actrabajoequipo.usecases.PostRecipeUseCase
import kotlinx.coroutines.launch

class FormRecipeViewModel(
    private val postRecipeUseCase: PostRecipeUseCase,
    private val findUserByIdUseCase: FindUserByIdUseCase,
    private val patchUserUseCase: PatchUserUseCase,
    private val firebaseManager: FirebaseManager,
    private val urlManager: UrlManager
) : ScopedViewModel(), PhotoCallBack {

    private var id: String? = null
    private var ingredientsWithoutEmpties = ArrayList<String>()

    sealed class ValidatedFields {
        object EmptyTitleRecipeError : ValidatedFields()
        object EmptyStepsRecipeError : ValidatedFields()
        object EmptyIngredientsError : ValidatedFields()
        object EmptyPhotoFieldError : ValidatedFields()
        object EmptyIdFieldError : ValidatedFields()
        object FormValidated : ValidatedFields()
    }

    sealed class SaveRecipe {
        object Success : SaveRecipe()
        object Error : SaveRecipe()
    }

    sealed class ImageUpload {
        object Success : ImageUpload()
        object InProgress : ImageUpload()
        object Error : ImageUpload()
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
        stepRecipe: String?,
        ingredients: ArrayList<String>
    ) {
        launch {
            if (urlManager.photoUrl == null) {
                _formState.postValue(ValidatedFields.EmptyPhotoFieldError)
            } else if (titleRecipe == null || titleRecipe.isEmpty()) {
                _formState.postValue(ValidatedFields.EmptyTitleRecipeError)
            } else if (stepRecipe == null || stepRecipe.isEmpty()) {
                _formState.postValue(ValidatedFields.EmptyStepsRecipeError)
            } else if (!editTextArrayListValidated(ingredients)) {
                _formState.postValue(ValidatedFields.EmptyIngredientsError)
            } else _formState.postValue(ValidatedFields.FormValidated)
        }
    }

    fun saveRecipe(
        idUser: String,
        titleRecipe: String,
        descriptionRecipe: String,
        stepRecipe: String
    ) {
        launch {
            val nodeRecipeId = postRecipeUseCase.invoke(
                Recipe(
                    id = null,
                    idUser = idUser,
                    name = titleRecipe,
                    description = descriptionRecipe,
                    imgUrl = urlManager.photoUrl ?: "",
                    ingredients = ingredientsWithoutEmpties,
                    preparation = stepRecipe,
                    favorite = false
                )
            )
            if (nodeRecipeId != null) {
                firebaseManager.fbAuth.currentUser?.let { firebaseUser ->

                    val user = findUserByIdUseCase.invoke(firebaseUser.uid)
                    val recipes: MutableList<String> = if (user.recipes != null) {
                        user.recipes!!
                    } else {
                        mutableListOf()
                    }

                    recipes.add(nodeRecipeId)
                    firebaseManager.returnUserUID()?.let { uidUser ->
                        patchUserUseCase.invoke(uidUser, User(null, null, recipes))
                        _recipeState.postValue(SaveRecipe.Success)
                    }
                }
            } else {
                _recipeState.postValue(SaveRecipe.Error)
            }
        }
    }

    private fun editTextArrayListValidated(ingredients: ArrayList<String>): Boolean {
        var isValid = false
        for (ingredient in ingredients) {
            if (!ingredient.isNullOrEmpty()) {
                isValid = true
                ingredientsWithoutEmpties.add(ingredient)
            }
        }
        return isValid
    }

    fun uploadImage(imageUri: Uri?) {
        launch {
            if (imageUri != null) {
                id = firebaseManager.returnIdKeyEntry()
                if (id != null)
                    firebaseManager.uploadPhotoRecipe(id, imageUri, this@FormRecipeViewModel)
                else
                    _formState.postValue(ValidatedFields.EmptyIdFieldError)
            }
        }
    }

    override fun onProgress(progress: Int) {
        launch {
            _progressUploadImage.postValue(progress)
            _imageUpload.postValue(ImageUpload.InProgress)
        }
    }

    override fun onComplete() {
    }

    override fun onSuccess(imageURL: String) {
        launch {
            _imageUpload.postValue(ImageUpload.Success)
            urlManager.photoUrl = imageURL
        }
    }

    override fun onFailure() {
        launch {
            _imageUpload.postValue(ImageUpload.Error)
        }
    }
}

data class UrlManager(var photoUrl: String? = null)
