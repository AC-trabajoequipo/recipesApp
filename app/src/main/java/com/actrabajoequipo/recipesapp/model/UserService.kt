package com.actrabajoequipo.recipesapp.model

import android.telecom.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("users.json")
    suspend fun postUser(@Body userDto: UserDto):Call
}