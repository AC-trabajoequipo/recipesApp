package com.actrabajoequipo.recipesapp.server

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDto(
    val name: String?,
    val email: String?
) : Parcelable