package com.actrabajoequipo.recipesapp.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth

class SignupViewModel() : ViewModel(), Scope by Scope.Impl() {

    sealed class UiSignup(){
        class State1 : UiSignup()
        class State2 : UiSignup()
        class State3 : UiSignup()
        class State4 : UiSignup()
        class State5 : UiSignup()
    }

    private val fbAuth = FirebaseAuth.getInstance()

    private val _registrado = MutableLiveData<SignupViewModel.UiSignup>()
    val registrado: LiveData<SignupViewModel.UiSignup> get() = _registrado



    init {
        initScope()
    }


    fun signup(name :String, email :String, password :String, passwordConfirm :String) {
        if(name.length > 1 && email.length > 1 && password.length > 5 && passwordConfirm.length > 5){
            if( password.trim().equals(passwordConfirm.trim())) {
                if (password.matches(Regex(".*[a-z].*"))
                    && password.matches(Regex(".*[A-Z].*"))
                    && password.matches(Regex(".*[0-9].*"))){

                    //CREAMOS EL USER
                    fbAuth.createUserWithEmailAndPassword(email.toString().trim(), password.toString().trim())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val user = fbAuth.currentUser
                                //ENVIAMOS MAIL DE CONFIRMACION
                                user?.sendEmailVerification()
                                _registrado.value = UiSignup.State1()
                            }else _registrado.value = UiSignup.State2()
                        }
                }else _registrado.value = UiSignup.State3()
            }else _registrado.value = UiSignup.State4()
        }else _registrado.value = UiSignup.State5()
    }
}

