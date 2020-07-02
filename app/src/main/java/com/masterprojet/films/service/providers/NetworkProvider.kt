package com.masterprojet.films.service.providers

import android.util.Log
import com.masterprojet.films.model.SignInModel
import com.masterprojet.films.model.UserBody
import com.masterprojet.films.service.NetworkAPI
import com.masterprojet.films.service.dto.SignInDTO
import com.masterprojet.films.service.mapper.SignInMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL = "https://projet-annuel-node.herokuapp.com/api/"

object NetworkProvider {
    private val networkAPI: NetworkAPI = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NetworkAPI::class.java)
    fun signIn(email: String, password: String,listener: NetworkListener<UserBody>){
        val signInInfo = SignInModel(email, password)
        networkAPI.signIn(signInInfo).enqueue(object : Callback<SignInDTO> {
            override fun onFailure(call: Call<SignInDTO>, t: Throwable) {
                listener.onError(t)
            }
            override fun onResponse(call: Call<SignInDTO>, response: Response<SignInDTO>) {
                if (response.code() == 200) {
                    //Toast.makeText(this@LoginActivity, "Login success!", Toast.LENGTH_SHORT).show()
                    val signInDTO = response.body()
                    signInDTO?.let {
                        val signInModel=SignInMapper().toSignIn(it)
                        listener.onSuccess(signInModel)
                    }
                } else {
                    Log.d("RESPONSE_USER", "Login failed")
                }
            }
        })
    }

}
interface NetworkListener<T>{
    fun onSuccess(data: T)
    fun onError(throwable: Throwable)
}