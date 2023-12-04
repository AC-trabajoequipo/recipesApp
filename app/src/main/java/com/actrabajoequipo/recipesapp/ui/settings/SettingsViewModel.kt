package com.actrabajoequipo.recipesapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.actrabajoequipo.domain.User
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import com.actrabajoequipo.usecases.DeleteUserUseCase
import com.actrabajoequipo.usecases.FindUserByIdUseCase
import com.actrabajoequipo.usecases.GetUsersUseCase
import com.actrabajoequipo.usecases.PatchUserUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val patchUserUseCase: PatchUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val findUserByIdUseCase: FindUserByIdUseCase,
    private val firebaseManager: FirebaseManager
) : ScopedViewModel() {

    sealed class ResultEditUsername() {
        class UsernameEditedSuccessfully : ResultEditUsername()
        class UsernameNoEdited : ResultEditUsername()
    }

    sealed class ResultEditEmail() {
        class EmailEditedSuccessfully : ResultEditEmail()
        class EmailNoEdited : ResultEditEmail()
    }

    sealed class ResultEditPassword() {
        class PasswordEditedSuccessfully : ResultEditPassword()
        class PasswordNoEdited : ResultEditPassword()
    }

    sealed class ResultDeleteUser() {
        class DeleteUserSuccessfully : ResultDeleteUser()
        class NoDeleteUser : ResultDeleteUser()
    }


    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser
    val currentUserUid = firebaseManager.fbAuth.currentUser!!.uid

    private val _resultEditUsername = MutableLiveData<ResultEditUsername>()
    val resultEditUsername: LiveData<ResultEditUsername> get() = _resultEditUsername

    private val _resultEditEmail = MutableLiveData<ResultEditEmail>()
    val resultEditEmail: LiveData<ResultEditEmail> get() = _resultEditEmail

    private val _resultEditPassword = MutableLiveData<ResultEditPassword>()
    val resultEditPassword: LiveData<ResultEditPassword> get() = _resultEditPassword

    private val _resultDeleteUser = MutableLiveData<ResultDeleteUser>()
    val resultDeleteUser: LiveData<ResultDeleteUser> get() = _resultDeleteUser

    init {
        initScope()
        getCurrentUser()
    }

    fun getCurrentUser() {
        var usersMap: Map<String, User>

        launch {
            usersMap = getUsersUseCase.invoke()
            usersMap.forEach {
                if (it.key.equals(currentUserUid)) {
                    _currentUser.value = it.value
                }
            }
        }
    }

    fun editUserName(newUsername: String) {
        launch {
            val responseEditUsername =
                patchUserUseCase.invoke(currentUserUid, User(newUsername, null, null))
            if (responseEditUsername != null) {
                _resultEditUsername.value = ResultEditUsername.UsernameEditedSuccessfully()
            } else {
                _resultEditUsername.value = ResultEditUsername.UsernameNoEdited()
            }
        }
    }

    fun editEmail(newEmail: String) {
        launch {
            firebaseManager.fbAuth.currentUser?.updateEmail(newEmail)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch {
                            patchUserUseCase.invoke(currentUserUid, User(null, newEmail, null))
                            firebaseManager.fbAuth.currentUser!!.sendEmailVerification()
                            firebaseManager.fbAuth.signOut()
                            _resultEditEmail.value = ResultEditEmail.EmailEditedSuccessfully()
                        }
                    } else {
                        _resultEditEmail.value = ResultEditEmail.EmailNoEdited()
                    }
                }

        }
    }

    fun editPassword() {
        launch {
            firebaseManager.fbAuth.sendPasswordResetEmail(firebaseManager.fbAuth.currentUser!!.email.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseManager.fbAuth.signOut()
                        _resultEditPassword.value = ResultEditPassword.PasswordEditedSuccessfully()
                    } else {
                        _resultEditPassword.value = ResultEditPassword.PasswordNoEdited()
                    }
                }
        }
    }

    fun deleteUser() {
        launch {
            firebaseManager.fbAuth.currentUser!!.delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        deleteUserUseCase.invoke(currentUserUid)
                        _resultDeleteUser.value = ResultDeleteUser.DeleteUserSuccessfully()
                    }
                } else {
                    _resultDeleteUser.value = ResultDeleteUser.NoDeleteUser()
                }
            }
        }
    }

    fun isGoogleAccount() :Boolean{
        if(firebaseManager.fbAuth.currentUser!!.providerData.get(0).displayName.equals("")){
            return false
        }else{
            return true
        }
    }

    override fun onCleared() {
        super.onCleared()
        destroyScope()
    }

    //BORRAR CUANDO YA AÑADA BIEN LA RECETA EN EL USER AL CREAR UNA NUEVA RECETA
    fun pruebaaa() {
        launch {
            var user = findUserByIdUseCase.invoke(firebaseManager.fbAuth.currentUser!!.uid)
            if (user != null) {
                var recipes: MutableList<String>?
                if (user.recipes != null) {
                    recipes = user.recipes
                } else {
                    recipes = mutableListOf()
                }
                recipes?.add("zzz")
                var responsePostRecipeInUser = patchUserUseCase.invoke(
                    firebaseManager.fbAuth.currentUser!!.uid,
                    User(null, null, recipes)
                )
                /*if(responsePostRecipeInUser.nodeId != null){

                }else{

                }*/
            } else {

            }
        }
    }
}