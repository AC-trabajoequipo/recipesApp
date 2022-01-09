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
class GetRecipesUseCaseTest {

    @Mock
    lateinit var recipesRepository: RecipesRepository

    lateinit var getRecipesUseCase: GetRecipesUseCase

    @Before
    fun setUp() {
        getRecipesUseCase = GetRecipesUseCase(recipesRepository)
    }

    @Test
    fun `invoke calls recipes repository`() {
        runBlocking {
            val recipes = listOf(mockedRecipe.copy(id = "1"))
            whenever(recipesRepository.getRecipes()).thenReturn(recipes)

            val result = getRecipesUseCase.invoke()

            Assert.assertEquals(recipes, result)
        }
    }
}