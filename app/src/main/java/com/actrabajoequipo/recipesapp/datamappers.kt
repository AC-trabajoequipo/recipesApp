package com.actrabajoequipo.recipesapp

import com.actrabajoequipo.domain.Recipe as DomainRecipe
import com.actrabajoequipo.domain.User as DomainUser
import com.actrabajoequipo.recipesapp.data.database.Recipe as RoomRecipe
import com.actrabajoequipo.recipesapp.server.RecipeDto as ServerRecipe
import com.actrabajoequipo.recipesapp.server.UserDto as ServerUser

fun ServerRecipe.toDomainRecipe(): DomainRecipe =
    DomainRecipe(
        id,
        idUser,
        name,
        description ?: "",
        imgUrl ?: "",
        ingredients,
        preparation ?: "",
        false
    )

fun DomainRecipe.toRoomRecipe(): RoomRecipe =
    RoomRecipe(
        id ?: "-1",
        idUser,
        name,
        description,
        imgUrl,
        ingredients,
        preparation,
        favorite
    )

fun DomainRecipe.toServerRecipe(): ServerRecipe =
    ServerRecipe(
        id,
        idUser,
        name,
        description,
        imgUrl,
        ingredients,
        preparation
    )

fun RoomRecipe.toDomainRecipe(): DomainRecipe =
    DomainRecipe(
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
        email,
        recipes
    )

fun DomainUser.toServerUser(): ServerUser =
    ServerUser(
        name,
        email,
        recipes
    )
