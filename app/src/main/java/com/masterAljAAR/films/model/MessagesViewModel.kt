package com.masterAljAAR.films.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.masterAljAAR.films.fragments.Message

class MessagesViewModel : ViewModel() {
    fun getMessages() : MutableList<Message>{
        //val user_img: String?, val user_name: String?, val rating: Double, val text: String, val dateTime: String
        val json: String="""[{
            "user_img":"","user_name":"aze","rating":3.0,"text":"blabala","datetime":"2018-11-03T12:45:30"}
            ]"""
        val googleJson : Gson =  Gson()
        val listType = object : TypeToken<MutableList<Message>>() { }.type
        return  googleJson.fromJson(json, listType)

    }

}