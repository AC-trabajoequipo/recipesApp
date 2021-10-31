package com.actrabajoequipo.recipesapp.model

import com.actrabajoequipo.domain.UserDto
import retrofit2.http.*

interface ApiService {

    ////////////////// R E C I P E S //////////////////
    @GET("recipes.json")
    suspend fun getRecipes() : MutableMap<String, RecipeDto>

    @POST("recipes.json")
    suspend fun postRecipe(@Body recipeDto: RecipeDto) :PostResponseDto



    ////////////////// U S E R S //////////////////
    @GET("users.json")
    suspend fun getUsers(): Map<String, UserDto>

    @PATCH("users/{uid}.json")
    suspend fun patchUser(@Path("uid") uid: String, @Body userDto: UserDto): PostResponseDto

    @PATCH("users/{uid}.json")
    suspend fun editUsername(@Path("uid") uid: String, @Body newUsername : UserDto)

    @PATCH("users/{uid}.json")
    suspend fun editEmail(@Path("uid") uid: String, @Body newEmail : UserDto)

    @DELETE("users/{uid}.json")
    suspend fun deleteUser(@Path("uid") uid: String)



    //No se usan de momento
    @POST("users.json")
    suspend fun postUser(@Body userDto: UserDto): PostResponseDto

    @PUT("users/-MljPbrZgjsv0DwFiRyI.json")
    suspend fun putUser(@Body userDto: UserDto): PostResponseDto
}