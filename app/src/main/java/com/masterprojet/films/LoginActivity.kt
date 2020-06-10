package com.masterprojet.films


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class LoginActivity :  AppCompatActivity()  {
    private var loginURL = "https://projet-annuel-node.herokuapp.com/api/auth/users"
    private lateinit var loginEmail : EditText
    private lateinit var loginPassword : EditText
    private lateinit var forgotPassword : TextView
    private lateinit var btnLogin : Button
    private var preferenceHelper: PreferenceHelper? = null
    private var mProgressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        forgotPassword.setOnClickListener {

        }
        btnLogin.setOnClickListener {
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
        btnLogin = findViewById(R.id.login)
        preferenceHelper = PreferenceHelper(this)
        mProgressBar = findViewById(R.id.progressBar)

    }


    @Throws(IOException::class, JSONException::class)
    private fun login() {
        showSimpleProgressDialog()


            Fuel.post(loginURL, listOf(
                "email" to  loginEmail.text.toString()
                , "password" to  loginPassword.text.toString()
            )).responseJson { _, response, result ->
                Log.d("plzzzzz", response.toString())
                if(response.statusCode!=400){
                    Log.d("plzzzzz", result.get().content)
                    onTaskCompleted(result.get().content)
                }else{
                    Toast.makeText(this@LoginActivity, getErrorMessage(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onTaskCompleted(response: String) {
        Log.d("responseJson", response)
        removeSimpleProgressDialog()  //will remove progress dialog
        if (isSuccess(response)) {
                saveInfo(response)
                Toast.makeText(this@LoginActivity, "L'authentification est réussi", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, getErrorMessage(), Toast.LENGTH_SHORT).show()
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
            if(jsonObject.optString("token")!=""){
                return true
            }
            return false

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return false
    }

    private fun getErrorMessage(): String {
        removeSimpleProgressDialog()
        return "Aucune information"
    }
    private fun showSimpleProgressDialog() {
                val visibility = if (mProgressBar!!.visibility == View.GONE) View.VISIBLE else View.GONE
                mProgressBar!!.visibility = visibility
    }

    private fun removeSimpleProgressDialog() {
        if (mProgressBar!!.visibility==View.VISIBLE) {
            mProgressBar!!.visibility=View.GONE
        }
    }

}