package com.actrabajoequipo.domain

data class User(
    val name: String?,
    val email: String?,
    val recipes: MutableList<String>?
)