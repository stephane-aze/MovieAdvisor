package com.masterprojet.films.web.mapper

import com.masterprojet.films.model.User
import com.masterprojet.films.web.dto.UserDTO

class UserMapper {

    fun mapUser(userDTO:UserDTO){
        return with(userDTO){
            User(name = firstName ?: "N/A",email = email ?: "N/A")
        }

    }
}