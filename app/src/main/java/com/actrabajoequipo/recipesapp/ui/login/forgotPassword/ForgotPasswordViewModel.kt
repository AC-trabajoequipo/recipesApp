package com.actrabajoequipo.recipesapp.ui.login.forgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val firebaseManager: FirebaseManager
) : ScopedViewModel() {

    sealed class ResultEditPassword {
        object PasswordEditedSuccessfully : ResultEditPassword()
        object PasswordNoEdited : ResultEditPassword()
    }

    private val _resultEditPassword = MutableLiveData<ResultEditPassword>()
    val resultEditPassword: LiveData<ResultEditPassword> get() = _resultEditPassword

    init {
        initScope()
    }

    fun editPassword(email: String) {
        launch {
            firebaseManager.fbAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseManager.fbAuth.signOut()
                    _resultEditPassword.value = ResultEditPassword.PasswordEditedSuccessfully
                } else {
                    _resultEditPassword.value = ResultEditPassword.PasswordNoEdited
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        destroyScope()
    }
}