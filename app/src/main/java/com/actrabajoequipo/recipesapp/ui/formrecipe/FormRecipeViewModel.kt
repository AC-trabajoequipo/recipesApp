package com.actrabajoequipo.recipesapp.ui.formrecipe

import android.net.Uri
import androidx.lifecycle.*
import com.actrabajoequipo.recipesapp.model.ManageFireBase
import com.actrabajoequipo.recipesapp.model.ManageFireBase.PhotoCallBack
import com.actrabajoequipo.recipesapp.model.RecipeDto
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.model.user.UserDto
import com.actrabajoequipo.recipesapp.model.user.UserRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class FormRecipeViewModel(private val recipesRepository: RecipesRepository) : ViewModel(), PhotoCallBack, Scope by Scope.Impl() {

    private var photoUrl: String? = null
    private var id: String? = null
    private var ingredientsWithoutEmpties = ArrayList<String>()
    private val fbAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository by lazy { UserRepository() }

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
            val responsePostRecipe = recipesRepository.postRecipe(
                RecipeDto(
                    id = null,
                    idUser = idUser,
                    name = titleRecipe,
                    description = descriptionRecipe,
                    imgUrl = photoUrl,
                    ingredients = ingredientsWithoutEmpties,
                    preparation = stepRecipe
                )
            )
            if (responsePostRecipe.nodeId != null){
                var user = userRepository.findUserById(fbAuth.currentUser!!.uid)
                if(user != null){
                    var recipes : MutableList<String>?
                    if (user.recipes != null){
                        recipes = user.recipes
                    }else{
                        recipes = mutableListOf()
                    }
                    recipes?.add(id!!)
                    var responsePostRecipeInUser = userRepository.patchRecipeInUser(fbAuth.currentUser!!.uid, UserDto(null, null, recipes))
                    if(responsePostRecipeInUser.nodeId != null){
                        _recipeState.postValue(SaveRecipe.Success())
                    }else{
                        _recipeState.postValue(SaveRecipe.Error())
                    }
                }else{
                    _recipeState.postValue(SaveRecipe.Error())
                }
            }else {
                _recipeState.postValue(SaveRecipe.Error())
            }
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