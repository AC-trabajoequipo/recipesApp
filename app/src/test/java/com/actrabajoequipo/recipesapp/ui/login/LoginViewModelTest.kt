package com.actrabajoequipo.recipesapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import com.actrabajoequipo.testshared.mockedUserEmail
import com.actrabajoequipo.testshared.mockedUserPassword
import com.actrabajoequipo.usecases.FindUserByIdUseCase
import com.actrabajoequipo.usecases.PatchUserUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findUserByIdUseCase: FindUserByIdUseCase

    @Mock
    lateinit var firebaseManager: FirebaseManager

    @Mock
    lateinit var patchUserUseCase: PatchUserUseCase

    @Mock
    lateinit var observer: Observer<LoginViewModel.UiLogin>

    @Mock
    lateinit var task: Task<AuthResult>

    @Mock
    lateinit var user: FirebaseUser

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(findUserByIdUseCase, firebaseManager, patchUserUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `login is successfull`() {
        coroutineScope.runBlockingTest {

            //GIVEN
            val email = mockedUserEmail
            val password = mockedUserPassword
            whenever(firebaseManager.fbAuth.signInWithEmailAndPassword(any(), any())).thenReturn(task)
            whenever(task.isSuccessful).thenReturn(true)
            whenever(firebaseManager.fbAuth.currentUser).thenReturn(user)
            whenever(user.isEmailVerified).thenReturn(true)

            //WHEN
            viewModel.logeado.observeForever(observer)

            //THEN
            verify(observer).onChanged(LoginViewModel.UiLogin.Success())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `login is wrongEmailOrPassword`() {
        coroutineScope.runBlockingTest {

            //GIVEN
            val email = "aaa@gmail.com"
            val password = "1234aA"
            whenever(viewModel.login(email, password)).thenReturn(null)

            //WHEN
            viewModel.logeado.observeForever(observer)

            //THEN
            verify(observer).onChanged(LoginViewModel.UiLogin.WrongEmailOrPassword())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `login is FillinFields`() {
        coroutineScope.runBlockingTest {

            //GIVEN
            val email = ""
            val password = "1234aA"
            whenever(viewModel.login(email, password)).thenReturn(null)

            //WHEN
            viewModel.logeado.observeForever(observer)

            //THEN
            verify(observer).onChanged(LoginViewModel.UiLogin.FillinFields())
        }
    }
}