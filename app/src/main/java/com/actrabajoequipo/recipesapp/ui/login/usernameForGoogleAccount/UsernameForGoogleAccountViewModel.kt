package com.actrabajoequipo.recipesapp.ui.login.usernameForGoogleAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actrabajoequipo.domain.User
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.ScopedViewModel
import com.actrabajoequipo.usecases.PatchUserUseCase
import kotlinx.coroutines.launch

class UsernameForGoogleAccountViewModel(
    private val patchUserUseCase: PatchUserUseCase,
    firebaseManager: FirebaseManager
) : ScopedViewModel() {

    sealed class ResultSetUsername {
        object SetUsernameSuccessfully : ResultSetUsername()
        object SetUsernameNoEdited : ResultSetUsername()
    }

    private val currentUser = firebaseManager.fbAuth.currentUser

    private val _resultSetUsername = MutableLiveData<ResultSetUsername>()
    val resultSetUsername: LiveData<ResultSetUsername> get() = _resultSetUsername

    init {
        initScope()
    }

    fun setUsername(username: String) {
        launch {
            val responsePatchUser =
                patchUserUseCase.invoke(currentUser!!.uid, User(username, currentUser.email, null))
            if (responsePatchUser != null) {
                _resultSetUsername.value = ResultSetUsername.SetUsernameSuccessfully
            } else {
                _resultSetUsername.value = ResultSetUsername.SetUsernameNoEdited
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        destroyScope()
    }
}