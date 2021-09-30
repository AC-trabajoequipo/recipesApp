package com.actrabajoequipo.recipesapp.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.model.user.UserDto
import com.actrabajoequipo.recipesapp.model.user.UserRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SignupViewModel() : ViewModel(), Scope by Scope.Impl() {

    private val userRepository: UserRepository by lazy { UserRepository() }

    sealed class UiSignup(){
        class UnconfirmedEmail : UiSignup()
        class EmailAlreadyRegistered : UiSignup()
        class PasswordRequirements : UiSignup()
        class PasswordsDoNotMatch : UiSignup()
        class FillinFields : UiSignup()
    }

    private val fbAuth = FirebaseAuth.getInstance()

    private val _registrado = MutableLiveData<UiSignup>()
    val registrado: LiveData<UiSignup> get() = _registrado



    init {
        initScope()
    }


    fun signup(name :String, email :String, password :String, passwordConfirm :String) {
        launch {
            if (name.length > 1 && email.length > 1 && password.length > 5 && passwordConfirm.length > 5) {
                if (password.trim().equals(passwordConfirm.trim())) {
                    if (password.matches(Regex(".*[a-z].*"))
                        && password.matches(Regex(".*[A-Z].*"))
                        && password.matches(Regex(".*[0-9].*"))
                    ) {

                        //CREAMOS EL USER
                        fbAuth.createUserWithEmailAndPassword(
                            email.toString().trim(),
                            password.toString().trim()
                        )
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val user = fbAuth.currentUser
                                    //ENVIAMOS MAIL DE CONFIRMACION
                                    user?.sendEmailVerification()
                                    _registrado.value = UiSignup.UnconfirmedEmail()
                                } else _registrado.value = UiSignup.EmailAlreadyRegistered()
                            }
                    } else _registrado.value = UiSignup.PasswordRequirements()
                } else _registrado.value = UiSignup.PasswordsDoNotMatch()
            } else _registrado.value = UiSignup.FillinFields()
        }
    }

    fun holaaa(){
        launch {
            val user = UserDto(5,"Fulanito","pruebaaa5")
            val code = userRepository.postUser(user)
            Log.e("msg", "El ID del usuario es: " + code.name)
        }
    }
}

