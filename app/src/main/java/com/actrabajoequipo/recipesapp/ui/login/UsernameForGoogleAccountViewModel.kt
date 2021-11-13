package com.actrabajoequipo.recipesapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.domain.UserDto
import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UsernameForGoogleAccountViewModel: ViewModel(), Scope by Scope.Impl(){

    sealed class ResultSetUsername(){
        class SetUsernameSuccessfully : ResultSetUsername()
        class SetUsernameNoEdited : ResultSetUsername()
    }

    private val fbAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository by lazy { UserRepository() }
    val currentUser = fbAuth.currentUser

    private val _resultSetUsername = MutableLiveData<ResultSetUsername>()
    val resultSetUsername: LiveData<ResultSetUsername> get() = _resultSetUsername



    init {
        initScope()
    }

    fun setUsername(username :String){
        launch {
            val reponsePatchUser = userRepository.patchUser(currentUser!!.uid,UserDto(username, currentUser.email, null))
            if (reponsePatchUser != null){
                _resultSetUsername.value = ResultSetUsername.SetUsernameSuccessfully()
            }else{
                _resultSetUsername.value = ResultSetUsername.SetUsernameNoEdited()
            }
        }
    }

    override fun onCleared() {
        destroyScope()
    }
}