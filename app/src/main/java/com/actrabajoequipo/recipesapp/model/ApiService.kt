package com.actrabajoequipo.recipesapp.model

import com.actrabajoequipo.recipesapp.model.user.PostUserDto
import com.actrabajoequipo.recipesapp.model.user.UserDto
import retrofit2.http.*

interface ApiService {

    @GET("recipes.json")
    suspend fun getRecipes(): List<RecipeDto>

    @POST("users/.json")
    suspend fun postUser(@Body userDto: UserDto): PostUserDto

    @PUT("users/-MljPbrZgjsv0DwFiRyI.json")
    suspend fun putUser(@Body userDto: UserDto): PostUserDto

    @PATCH("users/-MljPbrZgjsv0DwFiRyI.json")
    suspend fun patchUser(@Body userDto: UserDto): PostUserDto

    @POST("users/-MljPbrZgjsv0DwFiRyI.json")
    suspend fun postUser2(@Body userDto: UserDto): PostUserDto
}