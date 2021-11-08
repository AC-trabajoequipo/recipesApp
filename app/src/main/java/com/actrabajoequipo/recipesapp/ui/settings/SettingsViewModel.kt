package com.actrabajoequipo.recipesapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.actrabajoequipo.recipesapp.model.user.UserDto
import com.actrabajoequipo.recipesapp.model.user.UserRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SettingsViewModel: ViewModel(), Scope by Scope.Impl(){

    sealed class ResultEditUsername(){
        class UsernameEditedSuccessfully : ResultEditUsername()
        class UsernameNoEdited : ResultEditUsername()
    }

    sealed class ResultEditEmail(){
        class EmailEditedSuccessfully : ResultEditEmail()
        class EmailNoEdited : ResultEditEmail()
    }

    sealed class ResultEditPassword(){
        class PasswordEditedSuccessfully : ResultEditPassword()
        class PasswordNoEdited : ResultEditPassword()
    }

    sealed class ResultDeleteUser(){
        class DeleteUserSuccessfully : ResultDeleteUser()
        class NoDeleteUser : ResultDeleteUser()
    }

    private val fbAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository by lazy { UserRepository() }

    private val _currentUser = MutableLiveData<UserDto>()
    val currentUser: LiveData<UserDto> get() = _currentUser
    val currentUserUid = fbAuth.currentUser!!.uid

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

    fun getCurrentUser(){
        var usersMap :Map<String, UserDto>

        launch {
            usersMap = userRepository.getUsers()
            usersMap.forEach {
                if(it.key.equals(currentUserUid)){
                    _currentUser.value = it.value
                }
            }
        }
    }

    fun editUserName(newUsername :String){
        launch {
            val responseEditUsername = userRepository.editUsername(currentUserUid, UserDto(newUsername, null, null))
            if (responseEditUsername != null){
                _resultEditUsername.value = ResultEditUsername.UsernameEditedSuccessfully()
            }else{
                _resultEditUsername.value = ResultEditUsername.UsernameNoEdited()
            }
        }
    }

    fun editEmail(newEmail :String){
        launch {
            fbAuth.currentUser?.updateEmail(newEmail)?.addOnCompleteListener { task->
                if (task.isSuccessful){
                    viewModelScope.launch {
                        userRepository.editEmail(currentUserUid, UserDto(null, newEmail, null))
                        fbAuth.currentUser!!.sendEmailVerification()
                        fbAuth.signOut()
                        _resultEditEmail.value = ResultEditEmail.EmailEditedSuccessfully()
                    }
                }else{
                    _resultEditEmail.value = ResultEditEmail.EmailNoEdited()
                }
            }

        }
    }

    fun editPassword(){
        launch {
            fbAuth.sendPasswordResetEmail(fbAuth.currentUser!!.email.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fbAuth.signOut()
                    _resultEditPassword.value = ResultEditPassword.PasswordEditedSuccessfully()
                } else {
                    _resultEditPassword.value = ResultEditPassword.PasswordNoEdited()
                }
            }
        }
    }

    fun deleteUser(){
        launch {
            fbAuth.currentUser!!.delete().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    viewModelScope.launch {
                        userRepository.deleteUser(currentUserUid)
                        _resultDeleteUser.value = ResultDeleteUser.DeleteUserSuccessfully()
                    }
                }else{
                    _resultDeleteUser.value = ResultDeleteUser.NoDeleteUser()
                }
            }
        }
    }

    override fun onCleared() {
        destroyScope()
    }
}