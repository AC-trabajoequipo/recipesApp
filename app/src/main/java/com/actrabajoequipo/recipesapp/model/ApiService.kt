package com.actrabajoequipo.recipesapp.model

import com.actrabajoequipo.recipesapp.model.user.PostUserDto
import com.actrabajoequipo.recipesapp.model.user.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("recipes.json")
    suspend fun getRecipes(): List<RecipeDto>

    @POST("users.json")
    suspend fun postUser(@Body userDto: UserDto): PostUserDto
}