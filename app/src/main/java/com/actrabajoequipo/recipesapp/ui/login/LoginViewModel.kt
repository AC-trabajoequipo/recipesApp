package com.actrabajoequipo.recipesapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.Scope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseManager: FirebaseManager
) : ViewModel(), Scope by Scope.Impl() {

    sealed class UiLogin(){
        class Success : UiLogin()
        class UnconfirmedEmail : UiLogin()
        class WrongEmailOrPassword : UiLogin()
        class FillinFields : UiLogin()
    }


    private val _logeado = MutableLiveData<UiLogin>()
    val logeado: LiveData<UiLogin> get() = _logeado


    init {
        initScope()
    }

    fun login(email :String, password: String) {
        launch {
            if (email.length > 0 && password.length > 0) {
                //COMPROBAMOS LAS CREDENCIALES DEL USER
                firebaseManager.fbAuth.signInWithEmailAndPassword(email.trim(), password.trim())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = firebaseManager.fbAuth.currentUser
                            if (user!!.isEmailVerified) {
                                _logeado.value = UiLogin.Success()
                            } else {
                                _logeado.value = UiLogin.UnconfirmedEmail()
                            }
                        } else {
                            _logeado.value = UiLogin.WrongEmailOrPassword()
                        }
                    }
            } else {
                _logeado.value = UiLogin.FillinFields()
            }
        }
    }





    override fun onCleared() {
        destroyScope()
    }
}


