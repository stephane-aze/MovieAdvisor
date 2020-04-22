package com.masterAljAAR.films

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.GraphRequest.GraphJSONObjectCallback
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class LoginActivity :  AppCompatActivity()  {
    private var LoginURL = "https://projet-annuel-node.herokuapp.com/api/auth/users";
    lateinit var loginEmail : EditText
    lateinit var loginPassword : EditText
    lateinit var forgotPassword : TextView
    lateinit var btnlogin : Button
    private val LoginTask = 1
    private var preferenceHelper: PreferenceHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

        btnlogin.setOnClickListener {
            try {
                login()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
    private fun init(){
        forgotPassword = findViewById(R.id.forgotPassword)
        loginPassword = findViewById(R.id.LoginPassword)
        loginEmail = findViewById(R.id.LoginEmail)
        btnlogin = findViewById(R.id.login)
        preferenceHelper = PreferenceHelper(this)
    }


    @Throws(IOException::class, JSONException::class)
    private fun login() {


        try {

            Fuel.post(LoginURL, listOf(
                "email" to  loginEmail.text.toString()
                , "password" to  loginPassword.text.toString()
            )).responseJson { request, response, result ->
                Log.d("plzzzzz", result.get().content)
                onTaskCompleted(result.get().content,LoginTask)
            }
        } catch (e: Exception) {

        } finally {

        }
    }

    private fun onTaskCompleted(response: String, task: Int) {
        Log.d("responsejson", response)

        when (task) {
            LoginTask -> if (isSuccess(response)) {
                saveInfo(response)
                Toast.makeText(this@LoginActivity, "Login Successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                this.finish()
            } else {
                Toast.makeText(this@LoginActivity, getErrorMessage(response), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveInfo(response: String) {
        preferenceHelper!!.putIsLogin(true)
        try {
            val jsonObject = JSONObject(response)

            preferenceHelper!!.putEmail(jsonObject.getString("email"))

            preferenceHelper!!.putName(jsonObject.getString("pseudo"))
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    private fun isSuccess(response: String): Boolean {

        try {
            val jsonObject = JSONObject(response)
            if(jsonObject.optString("email")!=""){
                return true
            }
            return false

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return false
    }

    private fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("status")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return "No data"
    }


}