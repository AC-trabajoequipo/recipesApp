package com.actrabajoequipo.recipesapp.ui.addrecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel

class AddRecipeViewModel(
    private val firebaseManger: FirebaseManager
) : ScopedViewModel() {

    sealed class UserLogged {
        object Logged : UserLogged()
        object NotLogged : UserLogged()
    }

    private val _userLoggedState = MutableLiveData<UserLogged>()
    val userLoggedState: LiveData<UserLogged> get() = _userLoggedState

    fun isUserLogged() {
        if (firebaseManger.fbAuth.currentUser != null) {
            _userLoggedState.postValue(UserLogged.Logged)
        } else {
            _userLoggedState.postValue(UserLogged.NotLogged)
        }
    }
}