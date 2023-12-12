package com.actrabajoequipo.recipesapp.ui.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.actrabajoequipo.domain.User
import com.actrabajoequipo.usecases.FindUserByIdUseCase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import com.actrabajoequipo.usecases.PatchUserUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel(
    private val findUserByIdUseCase: FindUserByIdUseCase,
    private val firebaseManager: FirebaseManager,
    private val patchUserUseCase: PatchUserUseCase
) : ScopedViewModel() {

    sealed class UiLogin {
        object Success : UiLogin()
        object UnconfirmedEmail : UiLogin()
        object WrongEmailOrPassword : UiLogin()
        object FillInFields : UiLogin()
    }

    private val _loginModel = MutableLiveData<UiLogin>()
    val loginModel: LiveData<UiLogin> get() = _loginModel
    sealed class UiLoginWithGoogleAccount(){
        class LoggedForFirsTime : UiLoginWithGoogleAccount()
        class Success : UiLoginWithGoogleAccount()
        class NotSuccess : UiLoginWithGoogleAccount()
        class Error : UiLoginWithGoogleAccount()
    }

    private val _logeado = MutableLiveData<UiLogin>()
    val logeado: LiveData<UiLogin> get() = _logeado

    private val _logeadoGoogle = MutableLiveData<UiLoginWithGoogleAccount>()
    val logeadoGoogle: LiveData<UiLoginWithGoogleAccount> get() = _logeadoGoogle

    private val GOOGLE_SIGN_IN = 100

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

    fun postLoginWithGoogleAccount(requestCode: Int, resultCode: Int, data: Intent?){
        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    firebaseManager.fbAuth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                                viewModelScope.launch {
                                    try {
                                        firebaseManager.fbAuth.currentUser?.let { user ->
                                            findUserByIdUseCase.invoke(user.uid)
                                        }
                                        _logeadoGoogle.value = UiLoginWithGoogleAccount.Success()
                                    }catch (e: KotlinNullPointerException){
                                        firebaseManager.fbAuth.currentUser?.let { user ->
                                            patchUserUseCase.invoke(
                                                user.uid,
                                                User(user.displayName, user.email, null)
                                            )
                                        }
                                        _logeadoGoogle.value = UiLoginWithGoogleAccount.LoggedForFirsTime()
                                    }
                                }
                        }else{
                            _logeadoGoogle.value = UiLoginWithGoogleAccount.NotSuccess()
                        }
                    }
                }
            }catch (e: ApiException){
                _logeadoGoogle.value = UiLoginWithGoogleAccount.Error()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        destroyScope()
    }
}


