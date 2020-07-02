package com.masterprojet.films.service.mapper


import com.masterprojet.films.model.UserBody
import com.masterprojet.films.service.dto.SignInDTO

class SignInMapper {
    fun toSignIn(signInDTO: SignInDTO):UserBody{
        return with(signInDTO){
            UserBody(email?: "N/A",firstName?:"N/A", token?: "N/A")
        }
    }
}