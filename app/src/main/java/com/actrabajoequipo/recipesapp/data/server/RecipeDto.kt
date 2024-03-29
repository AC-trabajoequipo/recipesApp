package com.actrabajoequipo.recipesapp.data.server

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeDto(
    var id: String?,
    val idUser: String,
    val name: String,
    val description: String?,
    val imgUrl: String?,
    val ingredients: List<String>,
    val preparation: String?
) : Parcelable