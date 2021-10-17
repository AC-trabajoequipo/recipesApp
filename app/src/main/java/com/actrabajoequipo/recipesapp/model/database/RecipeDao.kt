package com.actrabajoequipo.recipesapp.model.database

import androidx.room.*
import com.actrabajoequipo.recipesapp.model.RecipeDto

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE id = :id")
    fun findById(id: String): Recipe

    @Query("SELECT COUNT(id) FROM Recipe")
    fun recipeCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipes(recipes: MutableList<Recipe>)

    @Update
    fun updateRecipe(recipe: Recipe)
}