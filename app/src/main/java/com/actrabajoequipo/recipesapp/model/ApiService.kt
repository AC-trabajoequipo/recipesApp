package com.actrabajoequipo.recipesapp.model

import com.actrabajoequipo.recipesapp.model.user.PostUserDto
import com.actrabajoequipo.recipesapp.model.user.UserDto
import retrofit2.http.*

interface ApiService {

    ////////////////// R E C I P E S //////////////////
    @GET("recipes.json")
    suspend fun getRecipes(): List<RecipeDto>



    ////////////////// U S E R S //////////////////
    @GET("users.json")
    suspend fun getUsers(): List<UserDto>

    @POST("users.json")
    suspend fun postUser(@Body userDto: UserDto): PostUserDto

    @PUT("users/-MljPbrZgjsv0DwFiRyI.json")
    suspend fun putUser(@Body userDto: UserDto): PostUserDto

    @PATCH("users/{uid}.json")
    suspend fun patchUser(@Path("uid") uid: String, @Body userDto: UserDto): PostUserDto


}