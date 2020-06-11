package com.masterprojet.films.web.dto

import com.google.gson.annotations.SerializedName

data class UserDTO (
    val email: String?,
    @SerializedName("pseudo") val firstName: String?
)