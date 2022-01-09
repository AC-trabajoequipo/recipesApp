package com.actrabajoequipo.recipesapp.data.server

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDto(
    val name: String?,
    val email: String?,
    val recipes: MutableList<String>?
) : Parcelable