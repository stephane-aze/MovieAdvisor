package com.masterprojet.films.model

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.masterprojet.films.fragments.Message

class MessagesViewModel : ViewModel() {
    fun getMessages(movieId:Int) : MutableList<Message> {
        //val user_img: String?, val user_name: String?, val rating: Double, val text: String, val dateTime: String
        if (movieId == 299536) {
            val json: String = """[{
            "user_img":"","user_name":"aze","rating":3.0,"text":"blabala","datetime":"2018-11-03T12:45:30","movieId":$movieId}
            ]"""
            val googleJson: Gson = Gson()
            val listType = object : TypeToken<MutableList<Message>>() {}.type
            return googleJson.fromJson(json, listType)


        } else {
            val json: String = """[]"""
            val googleJson: Gson = Gson()
            val listType = object : TypeToken<MutableList<Message>>() {}.type
            return googleJson.fromJson(json, listType)

        }
    }
    fun getMessagesAll() : MutableList<Message>{
            //val user_img: String?, val user_name: String?, val rating: Double, val text: String, val dateTime: String

                val json: String="""[{
                "user_img":"","user_name":"aze","rating":3.0,"text":"blabala","datetime":"2018-11-03T12:45:30","movieId":299536}
                ]"""
                val googleJson : Gson =  Gson()
                val listType = object : TypeToken<MutableList<Message>>() { }.type
                return  googleJson.fromJson(json, listType)

    }

}