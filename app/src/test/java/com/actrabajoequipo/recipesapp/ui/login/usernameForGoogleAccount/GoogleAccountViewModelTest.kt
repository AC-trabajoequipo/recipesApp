package com.actrabajoequipo.recipesapp.ui.login.usernameForGoogleAccount

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.domain.User
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import com.actrabajoequipo.testshared.mockedUserEmail
import com.actrabajoequipo.testshared.mockedUserID
import com.actrabajoequipo.testshared.mockedUserName
import com.actrabajoequipo.usecases.PatchUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class GoogleAccountViewModelTest {

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

    @ExperimentalCoroutinesApi
    @Test
    fun `when the service returns a value the state is SetUsernameSucessfully`(){

        coroutineScope.runBlockingTest {

            //GIVEN
            whenever(patchUserUseCase.invoke(mockedUserID, User(mockedUserName, mockedUserEmail, null))).thenReturn("value")
            //whenever(patchUserUseCase.invoke(any(), any())).thenReturn(null) --> Tabmién podría ponerse asi, ya que lo que le pasamos no nosinteresa, nos interesa solo la respuesta

            //WHEN
            viewModel.resultSetUsername.observeForever(observer)

            //THEN
            verify(observer).onChanged(UsernameForGoogleAccountViewModel.ResultSetUsername.SetUsernameSuccessfully())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when the service returns null the state is SetUsernameNoEdited`(){

        coroutineScope.runBlockingTest {

            //GIVEN
            whenever(patchUserUseCase.invoke(mockedUserID, User(mockedUserName, mockedUserEmail, null))).thenReturn(null)
            //whenever(patchUserUseCase.invoke(any(), any())).thenReturn(null) --> También podría ponerse asi, ya que lo que le pasamos no nosinteresa, nos interesa solo la respuesta

            //WHEN
            viewModel.resultSetUsername.observeForever(observer)

            //THEN
            verify(observer).onChanged(UsernameForGoogleAccountViewModel.ResultSetUsername.SetUsernameNoEdited())
        }
    }
}