package com.actrabajoequipo.recipesapp.ui.formrecipe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import com.actrabajoequipo.usecases.FindUserByIdUseCase
import com.actrabajoequipo.usecases.PatchUserUseCase
import com.actrabajoequipo.usecases.PostRecipeUseCase
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
class FormRecipeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var postRecipeUseCase: PostRecipeUseCase
    @Mock
    lateinit var findUserByIdUseCase: FindUserByIdUseCase
    @Mock
    lateinit var patchUserUseCase: PatchUserUseCase
    @Mock
    lateinit var firebaseManager: FirebaseManager
    @Mock
    lateinit var urlManager: UrlManager

    @Mock
    lateinit var observer: Observer<FormRecipeViewModel.ValidatedFields>

    private lateinit var viewModel: FormRecipeViewModel

    @Before
    fun setUp() {
        viewModel = FormRecipeViewModel(postRecipeUseCase, findUserByIdUseCase, patchUserUseCase, firebaseManager, urlManager)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when titleRecipe is null returns a EmptyTitleRecipeError`(){

        coroutineScope.runBlockingTest {

            //GIVEN
            whenever(urlManager.photoUrl).thenReturn("www.google.es")

            //WHEN
            viewModel.formState.observeForever(observer)
            viewModel.validatedFields(null, "fafafafs", arrayListOf())

            //THEN
            verify(observer).onChanged(FormRecipeViewModel.ValidatedFields.EmptyTitleRecipeError)
        }
    }
}