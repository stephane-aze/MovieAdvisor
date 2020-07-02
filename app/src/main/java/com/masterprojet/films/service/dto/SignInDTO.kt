package com.masterprojet.films.service.dto

import com.google.gson.annotations.SerializedName

data class SignInDTO(
    val email: String?,
    @SerializedName("pseudo") val firstName: String?,
    val token: String?
)