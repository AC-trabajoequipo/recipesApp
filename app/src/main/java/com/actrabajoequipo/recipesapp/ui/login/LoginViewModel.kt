package com.actrabajoequipo.recipesapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseManager: FirebaseManager
) : ScopedViewModel() {

    sealed class UiLogin {
        object Success : UiLogin()
        object UnconfirmedEmail : UiLogin()
        object WrongEmailOrPassword : UiLogin()
        object FillInFields : UiLogin()
    }

    private val _loginModel = MutableLiveData<UiLogin>()
    val loginModel: LiveData<UiLogin> get() = _loginModel

    init {
        initScope()
    }

    fun login(email: String, password: String) {
        launch {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                //COMPROBAMOS LAS CREDENCIALES DEL USER
                firebaseManager.fbAuth.signInWithEmailAndPassword(email.trim(), password.trim())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = firebaseManager.fbAuth.currentUser
                            if (user!!.isEmailVerified) {
                                _loginModel.value = UiLogin.Success
                            } else {
                                _loginModel.value = UiLogin.UnconfirmedEmail
                            }
                        } else {
                            _loginModel.value = UiLogin.WrongEmailOrPassword
                        }
                    }
            } else {
                _loginModel.value = UiLogin.FillInFields
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        destroyScope()
    }
}


