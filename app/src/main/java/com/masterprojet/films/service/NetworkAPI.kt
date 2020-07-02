package com.masterprojet.films.service


import com.masterprojet.films.model.SignInModel
import com.masterprojet.films.service.dto.SignInDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NetworkAPI {
    @Headers("Content-Type:application/json")
    @POST("auth/users")
    fun signIn(@Body info: SignInModel): Call<SignInDTO>
}