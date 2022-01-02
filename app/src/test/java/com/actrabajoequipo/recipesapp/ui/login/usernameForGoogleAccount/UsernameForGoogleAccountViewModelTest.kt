package com.actrabajoequipo.recipesapp.ui.login.usernameForGoogleAccount

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import com.actrabajoequipo.usecases.PatchUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class UsernameForGoogleAccountViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var patchUserUseCase: PatchUserUseCase

    @Mock
    lateinit var firebaseManager: FirebaseManager

    @Mock
    lateinit var observer: Observer<UsernameForGoogleAccountViewModel.ResultSetUsername>

    private lateinit var viewModel: UsernameForGoogleAccountViewModel

    @Before
    fun setUp() {
        viewModel = UsernameForGoogleAccountViewModel(patchUserUseCase, firebaseManager)
    }
}