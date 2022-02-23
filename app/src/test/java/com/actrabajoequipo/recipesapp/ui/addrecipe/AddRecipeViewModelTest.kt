package com.actrabajoequipo.recipesapp.ui.addrecipe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
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
class AddRecipeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var firebaseManager: FirebaseManager

    @Mock
    lateinit var observer: Observer<AddRecipeViewModel.UserLogged>

    private lateinit var viewModel: AddRecipeViewModel

    @Before
    fun setUp() {
        viewModel = AddRecipeViewModel(firebaseManager)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check if user is not logged`(){

        coroutineScope.runBlockingTest {

            //GIVEN
            whenever(firebaseManager.fbAuth.currentUser).thenReturn(null)

            //WHEN
            viewModel.userLoggedState.observeForever(observer)
            viewModel.isUserLogged()

            //THEN
            verify(observer).onChanged(AddRecipeViewModel.UserLogged.NotLogged)
        }
    }
}