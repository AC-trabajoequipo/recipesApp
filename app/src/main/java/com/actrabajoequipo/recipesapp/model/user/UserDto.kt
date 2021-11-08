package com.actrabajoequipo.recipesapp.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserDto(
    val name: String?,
    val email: String?,
    val recipes: List<String>?
) : Parcelable








