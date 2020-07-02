package com.masterprojet.films

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.masterprojet.films.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity()  {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private val TAG = "RegisterActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        with(binding){
            register.setOnClickListener {
                if(registerPassword.text.isNotBlank() && registerPassword.text.toString() == passwordConfirm.text.toString()){
                    toRegister()
                }else{
                    Log.d("AZE","ECHEC")
                }
            }
        }

    }

    private fun toRegister() {
        auth.createUserWithEmailAndPassword(binding.registerEmail.text.toString(), binding.registerPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Votre compte a été créé", Toast.LENGTH_LONG)
                        .show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }

            }
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.also{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            return
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

}