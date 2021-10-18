package com.actrabajoequipo.recipesapp.model

import com.actrabajoequipo.recipesapp.model.user.PostResponseDto
import com.actrabajoequipo.recipesapp.model.user.UserDto
import retrofit2.http.*

interface ApiService {

    ////////////////// R E C I P E S //////////////////
    @GET("recipes_2.json")
    suspend fun getRecipes() : Map<String, RecipeDto>

    @POST("recipes_2.json")
    suspend fun postRecipe(@Body recipeDto: RecipeDto) :PostResponseDto



    ////////////////// U S E R S //////////////////
    @GET("users.json")
    suspend fun getUsers(): Map<String, UserDto>

    @PATCH("users/{uid}.json")
    suspend fun patchUser(@Path("uid") uid: String, @Body userDto: UserDto): PostResponseDto

    @PATCH("users/{uid}.json")
    suspend fun editUsername(@Path("uid") uid: String, @Body newUsername :UserDto)

    @PATCH("users/{uid}.json")
    suspend fun editEmail(@Path("uid") uid: String, @Body newEmail :UserDto)

    @DELETE("users/{uid}.json")
    suspend fun deleteUser(@Path("uid") uid: String)


    //De momento no se usan
    @POST("users.json")
    suspend fun postUser(@Body userDto: UserDto): PostResponseDto

    @PUT("users/-MljPbrZgjsv0DwFiRyI.json")
    suspend fun putUser(@Body userDto: UserDto): PostResponseDto


}