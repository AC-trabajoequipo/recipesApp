import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.testshared.mockedRecipe
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RecipesRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var recipesRepository: RecipesRepository

    @Before
    fun setUp() {
        recipesRepository = RecipesRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getRecipes gets from local data source first`() {
        runBlocking {

            val localRecipes = listOf(mockedRecipe.copy("1"))
            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getRecipes()).thenReturn(localRecipes)

            val result = recipesRepository.getRecipes()

            assertEquals(localRecipes, result)
        }
    }

    @Test
    fun `getRecipes saves remote data to local`() {
        runBlocking {

            val remoteRecipes = listOf(mockedRecipe.copy("1"))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getRecipes()).thenReturn(remoteRecipes)

            recipesRepository.getRecipes()

            verify(localDataSource).saveRecipes(remoteRecipes)
        }
    }

    @Test
    fun `findById calls local data source`() {
        runBlocking {

            val recipe = mockedRecipe.copy(id = "7")
            whenever(localDataSource.findById("7")).thenReturn(recipe)

            val result = recipesRepository.findById("7")

            assertEquals(recipe, result)
        }
    }

    @Test
    fun `update updates local data source`() {
        runBlocking {

            val recipe = mockedRecipe.copy("1")

            recipesRepository.update(recipe)

            verify(localDataSource).update(recipe)
        }
    }

    @Test
    fun `search calls local data source`() {
        runBlocking {

            val query = "tortilla"
            val recipeList = listOf(mockedRecipe.copy("1"))
            whenever(localDataSource.search(query)).thenReturn(recipeList)

            val result = recipesRepository.search(query)

            assertEquals(recipeList, result)
        }
    }

    @Test
    fun `post calls remote data source`() {
        runBlocking {

            val recipe = mockedRecipe.copy(null)
            val recipeId = "5"
            whenever(remoteDataSource.postRecipe(recipe)).thenReturn(recipeId)

            val result = recipesRepository.postRecipe(recipe)

            assertEquals(recipeId, result)
        }
    }
}