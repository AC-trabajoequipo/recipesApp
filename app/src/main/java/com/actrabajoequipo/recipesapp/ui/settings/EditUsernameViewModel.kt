package com.actrabajoequipo.recipesapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actrabajoequipo.domain.UserDto
import com.actrabajoequipo.data.UserRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class EditUsernameViewModel: ViewModel(), Scope by Scope.Impl() {

    private val fbAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository by lazy { UserRepository() }

    private val _currentUsername = MutableLiveData<String>()
    val currentUsername: LiveData<String> get() = _currentUsername

    val currentUserUid = fbAuth.currentUser!!.uid


    init {
        initScope()
        getCurrentUsername()
    }

    fun getCurrentUsername(){
        var usersMap :Map<String, UserDto>

        launch {
            usersMap = userRepository.getUsers()
            usersMap.forEach {
                if(it.key.equals(currentUserUid)){
                    _currentUsername.value = it.value.name ?: ""
                }
            }
        }
    }

    fun editUserName(newUsername :String){
        launch {
            userRepository.editUsername(currentUserUid, UserDto(newUsername, null))
        }
    }


    override fun onCleared() {
        destroyScope()
    }
}