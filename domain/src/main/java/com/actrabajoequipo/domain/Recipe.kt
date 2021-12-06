package com.actrabajoequipo.domain

data class Recipe(
    val id: String?,
    val idUser: String,
    val name: String,
    val description: String,
    val imgUrl: String,
    val ingredients: List<String>,
    val preparation: String,
    val favorite: Boolean
)