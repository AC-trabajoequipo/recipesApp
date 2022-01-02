package com.actrabajoequipo.recipesapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import com.actrabajoequipo.recipesapp.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(getRecipesUseCase, searchRecipeUseCase)
    }
}