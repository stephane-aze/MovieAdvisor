package com.masterAljAAR.films
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class LoginActivity :  AppCompatActivity()  {
    lateinit var loginEmail : EditText
    lateinit var loginPassword : EditText
    lateinit var forgotPassword : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        forgotPassword = findViewById(R.id.forgotPassword)
        loginPassword = findViewById(R.id.LoginPassword)
        loginEmail = findViewById(R.id.LoginEmail)



    }
}