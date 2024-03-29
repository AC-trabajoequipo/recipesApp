package com.actrabajoequipo.recipesapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey
    val id: String,
    val idUser: String,
    val name: String,
    val description: String,
    val imgUrl: String,
    val ingredients: List<String>,
    val preparation: String,
    val favorite: Boolean
)