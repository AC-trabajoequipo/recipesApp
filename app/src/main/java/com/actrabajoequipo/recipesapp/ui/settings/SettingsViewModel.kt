package com.actrabajoequipo.recipesapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.actrabajoequipo.data.UserRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SettingsViewModel: ViewModel(), Scope by Scope.Impl(){

    sealed class ResultDeleteUser(){
        class deleteUserSuccessfully : ResultDeleteUser()
        class noDeleteUser : ResultDeleteUser()
    }

    private val fbAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository by lazy { UserRepository() }

    val currentUserUid = fbAuth.currentUser!!.uid

    private val _result = MutableLiveData<ResultDeleteUser>()
    val result: LiveData<ResultDeleteUser> get() = _result

    init {
        initScope()
    }

    fun deleteUser(){
        launch {
            fbAuth.currentUser!!.delete().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    viewModelScope.launch {
                        userRepository.deleteUser(currentUserUid)
                        _result.value = ResultDeleteUser.deleteUserSuccessfully()
                    }
                }else{
                    _result.value = ResultDeleteUser.noDeleteUser()
                }
            }
        }
    }

    override fun onCleared() {
        destroyScope()
    }
}