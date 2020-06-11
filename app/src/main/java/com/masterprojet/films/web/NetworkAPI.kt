package com.masterprojet.films.web

import com.masterprojet.films.web.dto.UserDTO
import retrofit2.Call
import retrofit2.http.GET

interface NetworkAPI {
    @GET("/users")
    fun getUser(): Call<UserDTO>
}