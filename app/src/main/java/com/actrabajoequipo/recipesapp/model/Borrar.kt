package com.actrabajoequipo.recipesapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Borrar(
    val c: String,
    val d: List<String>
) : Parcelable