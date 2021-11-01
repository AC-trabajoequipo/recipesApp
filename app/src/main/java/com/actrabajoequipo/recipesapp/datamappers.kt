package com.actrabajoequipo.recipesapp

import com.actrabajoequipo.domain.Recipe as DomainMovie
import com.actrabajoequipo.domain.User as DomainUser
import com.actrabajoequipo.recipesapp.data.database.Recipe as RoomRecipe
import com.actrabajoequipo.recipesapp.server.RecipeDto as ServerMovie
import com.actrabajoequipo.recipesapp.server.UserDto as ServerUser

fun ServerMovie.toDomainMovie(): DomainMovie =
    DomainMovie(
        id,
        idUser,
        name,
        description ?: "",
        imgUrl ?: "",
        ingredients,
        preparation ?: "",
        false
    )

fun DomainMovie.toRoomMovie(): RoomRecipe =
    RoomRecipe(
        id,
        idUser,
        name,
        description,
        imgUrl,
        ingredients,
        preparation,
        favorite
    )

fun DomainMovie.toServerMovie(): ServerMovie =
    ServerMovie(
        id,
        idUser,
        name,
        description,
        imgUrl,
        ingredients,
        preparation
    )

fun RoomRecipe.toDomainMovie(): DomainMovie =
    DomainMovie(
        id,
        idUser,
        name,
        description,
        imgUrl,
        ingredients,
        preparation,
        favorite
    )

fun ServerUser.toDomainUser(): DomainUser =
    DomainUser(
        name,
        email
    )

fun DomainUser.toServerUser(): ServerUser =
    ServerUser(
        name,
        email
    )
