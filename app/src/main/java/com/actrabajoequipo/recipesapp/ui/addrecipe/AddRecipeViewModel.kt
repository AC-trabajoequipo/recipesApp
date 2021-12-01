package com.actrabajoequipo.recipesapp.ui.addrecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.Scope

class AddRecipeViewModel(private val firebaseManger: FirebaseManager) : ViewModel(), Scope by Scope.Impl() {

    sealed class UserLogged {
        object Logged : UserLogged()
        object NotLogged: UserLogged()
    }

    private val _userLoggedState = MutableLiveData<AddRecipeViewModel.UserLogged>()
    val userLoggedState: LiveData<AddRecipeViewModel.UserLogged> get() = _userLoggedState

    fun isUserLogged(){
        if (firebaseManger.fbAuth != null) {
            _userLoggedState.postValue(AddRecipeViewModel.UserLogged.Logged)
        } else {
            _userLoggedState.postValue(AddRecipeViewModel.UserLogged.NotLogged)
        }
    }
}