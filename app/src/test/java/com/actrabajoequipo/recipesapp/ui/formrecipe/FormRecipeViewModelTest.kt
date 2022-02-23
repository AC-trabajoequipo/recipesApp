package com.actrabajoequipo.recipesapp.ui.formrecipe

import android.net.Uri
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

    @Mock
    lateinit var observerImageUpload: Observer<FormRecipeViewModel.ImageUpload>

    @Mock
    lateinit var observerSaveRecipe: Observer<FormRecipeViewModel.SaveRecipe>

    private lateinit var viewModel: FormRecipeViewModel

    private var ingredientsWithoutEmpties = arrayListOf("un ingrediente al menos")

    /*private val urimock = Uri.parse("file:///sdcard/img.png")*/

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

    @ExperimentalCoroutinesApi
    @Test
    fun `when stepRecipe is null returns a EmptyStepsRecipeError`(){

        coroutineScope.runBlockingTest {

            //GIVEN
            whenever(urlManager.photoUrl).thenReturn("www.google.es")

            //WHEN
            viewModel.formState.observeForever(observer)
            viewModel.validatedFields("title recipe", null, arrayListOf())

            //THEN
            verify(observer).onChanged(FormRecipeViewModel.ValidatedFields.EmptyStepsRecipeError)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when all ingredients recipe is null or empty returns a EmptyIngredientsError`(){

        coroutineScope.runBlockingTest {

            //GIVEN
            whenever(urlManager.photoUrl).thenReturn("www.google.es")

            //WHEN
            viewModel.formState.observeForever(observer)
            viewModel.validatedFields("title recipe", "fafafafs",
                arrayListOf("","","")
            )

            //THEN
            verify(observer).onChanged(FormRecipeViewModel.ValidatedFields.EmptyIngredientsError)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `image uploaded successfully`(){

        coroutineScope.runBlockingTest {

            //GIVEN
            whenever(firebaseManager.returnIdKeyEntry()).thenReturn("asdfasdfasdfasdf")

            //WHEN
            viewModel.imageUpload.observeForever(observerImageUpload)
            //viewModel.uploadImage(urimock)

            //THEN
            verify(observerImageUpload).onChanged(FormRecipeViewModel.ImageUpload.Success)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check that all form fields are valid`(){

        coroutineScope.runBlockingTest {

            //GIVEN
            whenever(urlManager.photoUrl).thenReturn("www.google.es")

            //WHEN
            viewModel.formState.observeForever(observer)
            viewModel.validatedFields("title recipe", "fafafafs",
                ingredientsWithoutEmpties
            )

            //THEN
            verify(observer).onChanged(FormRecipeViewModel.ValidatedFields.FormValidated)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check that the recipe was uploaded correctly`(){

        coroutineScope.runBlockingTest {

            //GIVEN
            whenever(urlManager.photoUrl).thenReturn("www.google.es")

            //WHEN
            viewModel.recipeState.observeForever(observerSaveRecipe)
            viewModel.saveRecipe("idUser",
                "titleRecipe",
                "descriptionRecipe",
                "stepRecipe")

            //THEN
            verify(observerSaveRecipe).onChanged(FormRecipeViewModel.SaveRecipe.Success)
        }
    }
}

