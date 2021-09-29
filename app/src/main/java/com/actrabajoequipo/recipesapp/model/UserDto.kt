package com.actrabajoequipo.recipesapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDto(
    val id: Int,
    val name: String,
    val email: String
) : Parcelable