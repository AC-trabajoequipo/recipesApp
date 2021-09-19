package com.actrabajoequipo.recipesapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe (val titleRecipe: String, val ingredients: ArrayList<String>, val stepRecipe: String) : Parcelable
