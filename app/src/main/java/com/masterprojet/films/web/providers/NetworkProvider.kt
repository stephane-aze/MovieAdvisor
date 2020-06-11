package com.masterprojet.films.web.providers

import android.util.Log
import com.masterprojet.films.web.dto.UserDTO
import com.masterprojet.films.web.NetworkAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL = "efef"

class NetworkProvider {
    val networkAPI:NetworkAPI = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NetworkAPI::class.java)
    fun getUser(listener: NetworkListener<UserDTO>){
        networkAPI.getUser().enqueue(object : Callback<UserDTO>{
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                val userDTO = response.body()
                userDTO?.let {
                    listener.onSuccess(userDTO)
                }
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.e("USER", "Error : $t")
            }

        })
    }

}
interface NetworkListener<T>{
    fun onSuccess(data: T)
    fun onError(throwable: Throwable)
}