package com.actrabajoequipo.recipesapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeDto(
    val id: String,
    val name: String,
    val description: String?,
    val imgUrl: String?,
    val ingredients: List<String>,
    val preparation: String?
) : Parcelable