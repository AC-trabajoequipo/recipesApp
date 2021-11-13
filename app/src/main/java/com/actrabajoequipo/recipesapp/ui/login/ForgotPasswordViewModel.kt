package com.actrabajoequipo.recipesapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.ui.Scope
import com.actrabajoequipo.recipesapp.ui.settings.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ForgotPasswordViewModel: ViewModel(), Scope by Scope.Impl(){

    sealed class ResultEditPassword(){
        class PasswordEditedSuccessfully : ResultEditPassword()
        class PasswordNoEdited : ResultEditPassword()
    }

    private val fbAuth = FirebaseAuth.getInstance()

    private val _resultEditPassword = MutableLiveData<ResultEditPassword>()
    val resultEditPassword: LiveData<ResultEditPassword> get() = _resultEditPassword


    init {
        initScope()
    }

    fun editPassword(email :String){
        launch {
            fbAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fbAuth.signOut()
                    _resultEditPassword.value = ResultEditPassword.PasswordEditedSuccessfully()
                } else {
                    _resultEditPassword.value = ResultEditPassword.PasswordNoEdited()
                }
            }
        }
    }

    override fun onCleared() {
        destroyScope()
    }
}