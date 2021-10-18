package com.actrabajoequipo.recipesapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.model.user.UserDto
import com.actrabajoequipo.recipesapp.model.user.UserRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class EditPasswordViewModel: ViewModel(), Scope by Scope.Impl(){

    sealed class ResultEditPassword(){
        class passwordEditedSuccessfully : ResultEditPassword()
        class passwordNoEdited : ResultEditPassword()
    }

    private val fbAuth = FirebaseAuth.getInstance()

    val currentUserUid = fbAuth.currentUser!!.uid

    private val _result = MutableLiveData<ResultEditPassword>()
    val result: LiveData<ResultEditPassword> get() = _result


    init {
        initScope()
    }

    fun editPassword(){
        launch {
            fbAuth.sendPasswordResetEmail(fbAuth.currentUser!!.email.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _result.value = ResultEditPassword.passwordEditedSuccessfully()
                } else {
                    _result.value = ResultEditPassword.passwordNoEdited()
                }
            }
        }
    }

    override fun onCleared() {
        destroyScope()
    }
}