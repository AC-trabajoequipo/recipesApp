package com.actrabajoequipo.recipesapp.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.actrabajoequipo.domain.User
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import com.actrabajoequipo.usecases.PatchUserUseCase
import kotlinx.coroutines.launch

class SignupViewModel(
    private val patchUserUseCase: PatchUserUseCase,
    private val firebaseManager: FirebaseManager
) : ScopedViewModel() {

    sealed class UiSignup {
        object UnconfirmedEmail : UiSignup()
        object EmailAlreadyRegistered : UiSignup()
        object PasswordRequirements : UiSignup()
        object PasswordsDoNotMatch : UiSignup()
        object FillinFields : UiSignup()
    }

    private val _registered = MutableLiveData<UiSignup>()
    val registered: LiveData<UiSignup> get() = _registered

    init {
        initScope()
    }

    fun signup(name: String, email: String, password: String, passwordConfirm: String) {
        launch {
            if (name.length > 1 && email.length > 1 && password.length > 5 && passwordConfirm.length > 5) {
                if (password.trim() == passwordConfirm.trim()) {
                    if (password.matches(Regex(".*[a-z].*"))
                        && password.matches(Regex(".*[A-Z].*"))
                        && password.matches(Regex(".*[0-9].*"))
                    ) {

                        //CREAMOS EL USER
                        firebaseManager.fbAuth.createUserWithEmailAndPassword(
                            email.trim(),
                            password.trim()
                        )
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val user = firebaseManager.fbAuth.currentUser
                                    //ENVIAMOS MAIL DE CONFIRMACION
                                    user?.sendEmailVerification()
                                    _registered.value = UiSignup.UnconfirmedEmail
                                    //ANADIMOS EL USER A NUESTRA BD
                                    viewModelScope.launch {
                                        val nodeId = patchUserUseCase.invoke(
                                            firebaseManager.fbAuth.currentUser!!.uid,
                                            User(name, email, null)
                                        )
                                        Log.d("msg", "El ID del usuario es: $nodeId")
                                    }
                                } else _registered.value = UiSignup.EmailAlreadyRegistered
                            }
                    } else _registered.value = UiSignup.PasswordRequirements
                } else _registered.value = UiSignup.PasswordsDoNotMatch
            } else _registered.value = UiSignup.FillinFields
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}



