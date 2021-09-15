package com.actrabajoequipo.recipesapp.ui.login

import android.app.UiModeManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel(private val email: String, private val password: String) : ViewModel(), Scope by Scope.Impl() {

    sealed class UiLogin(){
        class State1 : UiLogin()
        class State2 : UiLogin()
        class State3 : UiLogin()
        class State4 : UiLogin()
    }

    private val fbAuth = FirebaseAuth.getInstance()
    private val GOOGLE_SIGN_IN = 100


    private val _logeado = MutableLiveData<UiLogin>()
    val logeado: LiveData<UiLogin>
        get() {
            if (_logeado.value == null){
                refresh()
            }
            return _logeado
        }


    init {
        initScope()
    }

    private fun refresh() {
        launch {
            if (email.length > 0 && password.length > 0) {
                //COMPROBAMOS LAS CREDENCIALES DEL USER
                fbAuth.signInWithEmailAndPassword(
                    email.toString().trim(),
                    password.toString().trim()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = fbAuth.currentUser
                            if (user!!.isEmailVerified) {
                                _logeado.value = UiLogin.State1()
                            } else {
                                _logeado.value = UiLogin.State2()
                            }
                        } else {
                            _logeado.value = UiLogin.State3()
                        }
                    }
            } else {
                _logeado.value = UiLogin.State4()
            }
        }
    }





    override fun onCleared() {
        destroyScope()
    }
}


@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val email: String, private val password: String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(email,password) as T
    }
}