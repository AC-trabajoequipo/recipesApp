package com.actrabajoequipo.recipesapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeDto(
    val id: Int,
    val name: String,
    val description: String?,
    val imgUrl: String?
) : Parcelable