package com.actrabajoequipo.recipesapp.ui.settings

import androidx.lifecycle.ViewModel
import com.actrabajoequipo.recipesapp.model.user.UserDto
import com.actrabajoequipo.recipesapp.model.user.UserRepository
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class EditUsernameViewModel: ViewModel(), Scope by Scope.Impl() {

    private val fbAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository by lazy { UserRepository() }


    init {
        initScope()
    }


    fun getCurrentUsername() :String{
        lateinit var usersList :List<UserDto>
        var currentUsername = ""
        val currentUserUid = fbAuth.currentUser!!.uid

        launch {
            usersList = userRepository.getUsers()
            usersList.forEach {
                if(it.equals(currentUserUid)){
                    //currentUsername = it.name
                }
            }
        }

        return currentUsername
    }


    override fun onCleared() {
        destroyScope()
    }
}