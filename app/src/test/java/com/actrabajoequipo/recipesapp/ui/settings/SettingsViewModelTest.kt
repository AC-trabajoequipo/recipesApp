package com.actrabajoequipo.recipesapp.ui.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import com.actrabajoequipo.usecases.DeleteUserUseCase
import com.actrabajoequipo.usecases.FindUserByIdUseCase
import com.actrabajoequipo.usecases.GetUsersUseCase
import com.actrabajoequipo.usecases.PatchUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getUsersUseCase: GetUsersUseCase

    @Mock
    lateinit var patchUserUseCase: PatchUserUseCase

    @Mock
    lateinit var deleteUserUseCase: DeleteUserUseCase

    @Mock
    lateinit var findUserByIdUseCase: FindUserByIdUseCase

    @Mock
    lateinit var firebaseManager: FirebaseManager

    @Mock
    lateinit var observerEditUsername: Observer<SettingsViewModel.ResultEditUsername>

    @Mock
    lateinit var observerEditEmail: Observer<SettingsViewModel.ResultEditEmail>

    @Mock
    lateinit var observerEditPassword: Observer<SettingsViewModel.ResultEditPassword>

    @Mock
    lateinit var observerDeleteUser: Observer<SettingsViewModel.ResultDeleteUser>

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        viewModel = SettingsViewModel(getUsersUseCase, patchUserUseCase, deleteUserUseCase, findUserByIdUseCase, firebaseManager)
    }
}