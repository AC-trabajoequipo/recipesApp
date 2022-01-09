package com.actrabajoequipo.recipesapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import com.actrabajoequipo.testshared.mockedRecipe
import com.actrabajoequipo.usecases.FindRecipeByIdUseCase
import com.actrabajoequipo.usecases.UpdateRecipeUseCase
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
class DetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var findRecipeByIdUseCase: FindRecipeByIdUseCase

    @Mock
    lateinit var updateRecipeUseCase: UpdateRecipeUseCase

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    private lateinit var detailViewModel: DetailViewModel
    private var id = "1"

    @Before
    fun setUp() {
        detailViewModel =
            DetailViewModel(id, findRecipeByIdUseCase, updateRecipeUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `observing LiveData finds the recipe`() =

        coroutineScope.runBlockingTest {

            //GIVEN
            val recipe = mockedRecipe.copy(id = "1")
            whenever(findRecipeByIdUseCase.invoke("1")).thenReturn(recipe)

            //WHEN
            detailViewModel.uiModel.observeForever(observer)

            //THEN
            verify(observer).onChanged(DetailViewModel.UiModel(recipe))
        }
}