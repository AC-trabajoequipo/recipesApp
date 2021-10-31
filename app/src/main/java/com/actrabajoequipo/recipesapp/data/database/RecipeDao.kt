package com.actrabajoequipo.recipesapp.data.database

import androidx.room.*

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE id = :id")
    fun findById(id: String): Recipe

    @Query("SELECT COUNT(id) FROM Recipe")
    fun recipeCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipes(recipes: List<Recipe>)

    @Update
    fun updateRecipe(recipe: Recipe)
}