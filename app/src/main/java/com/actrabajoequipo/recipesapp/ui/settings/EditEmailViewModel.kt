package com.actrabajoequipo.recipesapp.ui.settings

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.model.user.UserDto
import com.actrabajoequipo.recipesapp.model.user.UserRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.actrabajoequipo.recipesapp.ui.signup.SignupViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class EditEmailViewModel: ViewModel(), Scope by Scope.Impl() {

    sealed class ResultEditEmail(){
        class emailEditedSuccessfully : ResultEditEmail()
        class emailNoEdited : ResultEditEmail()
    }

    private val fbAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository by lazy { UserRepository() }
    val fragment = EditEmailFragment()

    private val _currentEmail = MutableLiveData<String>()
    val currentEmail: LiveData<String> get() = _currentEmail

    private val _result = MutableLiveData<ResultEditEmail>()
    val result: LiveData<ResultEditEmail> get() = _result

    val currentUserUid = fbAuth.currentUser!!.uid

    init {
        initScope()
        getCurrentEmail()
    }


    fun getCurrentEmail(){
        var usersMap :Map<String, UserDto>

        launch {
            usersMap = userRepository.getUsers()
            usersMap.forEach {
                if(it.key.equals(currentUserUid)){
                    _currentEmail.value = it.value.email ?: ""
                }
            }
        }
    }

    fun editEmail(newEmail :String){
        launch {
            fbAuth.currentUser?.updateEmail(newEmail)?.addOnCompleteListener { task->
                if (task.isSuccessful){
                    viewModelScope.launch {
                        userRepository.editEmail(currentUserUid, UserDto(null, newEmail))
                        fbAuth.currentUser!!.sendEmailVerification()
                        fbAuth.signOut()
                        _result.value = ResultEditEmail.emailEditedSuccessfully()
                    }
                }else{
                    _result.value = ResultEditEmail.emailNoEdited()
                }
            }

        }
    }


    override fun onCleared() {
        destroyScope()
    }
}