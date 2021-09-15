package com.actrabajoequipo.recipesapp.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth

class SignupViewModel(private val name: String,
                      private val email: String,
                      private val password: String,
                      private val passwordConfirm: String) : ViewModel(), Scope by Scope.Impl() {

    sealed class UiSignup(){
        class State1 : UiSignup()
        class State2 : UiSignup()
        class State3 : UiSignup()
        class State4 : UiSignup()
        class State5 : UiSignup()
    }

    private val fbAuth = FirebaseAuth.getInstance()

    private val _registrado = MutableLiveData<SignupViewModel.UiSignup>()
    val registrado: LiveData<SignupViewModel.UiSignup>
        get() {
            if (_registrado.value == null){
                refresh()
            }
            return _registrado
        }


    init {
        initScope()
    }


    private fun refresh() {
        if(name.length > 1 && email.length > 1 && password.length > 5 && passwordConfirm.length > 5){
            if( password.toString().trim().equals(passwordConfirm.toString().trim())) {
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


@Suppress("UNCHECKED_CAST")
class SignupViewModelFactory(private val name: String, private val email: String, private val password: String, private val passwordConfirm: String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignupViewModel(name,email,password, passwordConfirm) as T
    }
}