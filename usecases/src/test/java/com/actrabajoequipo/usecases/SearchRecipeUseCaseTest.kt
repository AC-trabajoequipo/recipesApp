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
class SearchRecipeUseCaseTest {

    @Mock
    lateinit var recipesRepository: RecipesRepository

    lateinit var searchRecipeUseCase: SearchRecipeUseCase

    @Before
    fun setUp() {
        searchRecipeUseCase = SearchRecipeUseCase(recipesRepository)
    }

    @Test
    fun `invoke return a recipe`() = runBlocking {
        val query = "cocido"
        val recipe = mockedRecipe.copy(id = "1")
        whenever(recipesRepository.search(query)).thenReturn(listOf(recipe))

        val result = searchRecipeUseCase.invoke(query)

        Assert.assertEquals(listOf(recipe), result)
    }

}