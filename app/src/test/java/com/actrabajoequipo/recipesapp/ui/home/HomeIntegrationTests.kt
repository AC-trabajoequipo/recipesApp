package com.actrabajoequipo.recipesapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.FakeLocalDataSource
import com.actrabajoequipo.recipesapp.defaultFakeRecipes
import com.actrabajoequipo.recipesapp.di.DaggerTestAppComponent
import com.actrabajoequipo.recipesapp.di.TestAppComponent
import com.actrabajoequipo.recipesapp.mockedRecipe
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class HomeIntegrationTests {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<HomeViewModel.UIModel> = mock()
    private val component: TestAppComponent = DaggerTestAppComponent.factory().create()
    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = component.plus(HomeModule()).homeViewModel
        localDataSource = component.localDataSource as FakeLocalDataSource
        localDataSource.recipes = emptyList()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `data is loaded from server when local source is empty`() = coroutineScope.runBlockingTest {

        viewModel.uiModel.observeForever(observer)

        verify(observer).onChanged(
            ArgumentMatchers.refEq(
                HomeViewModel.UIModel.Content(
                    defaultFakeRecipes
                )
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `data is loaded from local source when available`() = coroutineScope.runBlockingTest {
        val fakeLocalRecipes = listOf(mockedRecipe.copy("10"), mockedRecipe.copy("11"))
        localDataSource = component.localDataSource as FakeLocalDataSource
        localDataSource.recipes = fakeLocalRecipes
        viewModel.uiModel.observeForever(observer)

        verify(observer).onChanged(
            ArgumentMatchers.refEq(
                HomeViewModel.UIModel.Content(
                    fakeLocalRecipes
                )
            )
        )
    }
}