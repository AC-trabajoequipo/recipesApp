package com.actrabajoequipo.recipesapp.data.server

import retrofit2.http.*

interface ApiService {

    ////////////////// R E C I P E S //////////////////
    @GET("recipes.json")
    suspend fun getRecipes(): MutableMap<String, RecipeDto>

    @POST("recipes.json")
    suspend fun postRecipe(@Body recipeDto: RecipeDto): PostResponseDto


    ////////////////// U S E R S //////////////////
    @GET("users.json")
    suspend fun getUsers(): Map<String, UserDto>

    @PATCH("users/{uid}.json")
    suspend fun patchUser(@Path("uid") uid: String, @Body userDto: UserDto): PostResponseDto

    @DELETE("users/{uid}.json")
    suspend fun deleteUser(@Path("uid") uid: String)

    @GET("users/{uid}.json")
    suspend fun findUserById(@Path("uid")uid: String) : UserDto
}