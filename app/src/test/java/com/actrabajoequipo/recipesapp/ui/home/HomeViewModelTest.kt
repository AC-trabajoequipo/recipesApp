package com.actrabajoequipo.recipesapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import com.actrabajoequipo.testshared.mockedRecipe
import com.actrabajoequipo.usecases.GetRecipesUseCase
import com.actrabajoequipo.usecases.SearchRecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getRecipesUseCase: GetRecipesUseCase

    @Mock
    lateinit var searchRecipeUseCase: SearchRecipeUseCase

    @Mock
    lateinit var observer: Observer<HomeViewModel.UIModel>

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(getRecipesUseCase, searchRecipeUseCase)
    }

    @Test
    fun `recipes are loaded`() {
        coroutineScope.runBlockingTest {

            val recipes = listOf(mockedRecipe.copy(id = "1"))
            whenever(getRecipesUseCase.invoke()).thenReturn(recipes)

            viewModel.uiModel.observeForever(observer)

            viewModel.refresh()

            verify(observer).onChanged(HomeViewModel.UIModel.Content(recipes))
        }
    }
}