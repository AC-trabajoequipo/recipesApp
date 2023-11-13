package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.testshared.mockedRecipe
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class FindRecipeByIdUseCaseTest {

    @Mock
    lateinit var recipesRepository: RecipesRepository

    lateinit var findRecipeByIdUseCase: FindRecipeByIdUseCase

    @Before
    fun setUp() {
        findRecipeByIdUseCase = FindRecipeByIdUseCase(recipesRepository)
    }

    @Test
    fun `invoke calls recipes repository`() {
        runBlocking {

            val recipe = mockedRecipe.copy(id = "1")
            whenever(recipesRepository.findById("1")).thenReturn(recipe)

            val result = findRecipeByIdUseCase.invoke("1")

            Assert.assertEquals(recipe, result)
        }
    }
}