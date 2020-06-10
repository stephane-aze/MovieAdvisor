package com.masterprojet.films

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import org.json.JSONException

const val RC_SIGN_IN = 9001

class HomeActivity :  AppCompatActivity() {
    private lateinit var loginBtn : Button
    private lateinit var createUser : TextView
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var loginButtonFacebook : LoginButton
    private lateinit var signInButton: SignInButton
    private lateinit var callbackManager: CallbackManager
    private val emailValue = "email"
    //private lateinit var accessTokenTracker: AccessTokenTracker
    private var btnTest: Button? = null
    private var preferenceHelper: PreferenceHelper? = null
    private val tag = "HomeActivity"


    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show()
            
            onLoggedIn(account)
        } else {
            Log.d(tag, "Not logged in")
        }

        // updateUI(account)
    }

    private fun onLoggedIn(account: GoogleSignInAccount ) {

            preferenceHelper?.let{
                it.putEmail(account.email!!)
                it.putName(account.givenName!!)
                it.putIsLogin(true)
            }
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun init(){
        // Assigned the elements in the var
        createUser = findViewById(R.id.new_user)
        loginBtn = findViewById(R.id.buttonLogin)
        signInButton = findViewById(R.id.sign_in_button)
        btnTest =findViewById(R.id.btnTest)
        preferenceHelper = PreferenceHelper(this)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()

        //Pour faire les tests
        btnTest!!.setOnClickListener {

        }


        // Google
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient (this, gso)
        signInButton.setOnClickListener{
            signInWithGoogle()
        }

        // Redirection Activity
        loginBtn.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            }
        createUser.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)

        }
        //Facebook
        callbackManager = CallbackManager.Factory.create()

        loginButtonFacebook = findViewById(R.id.login_button)
        loginButtonFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this,listOf(emailValue,"public_profile"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        graphLoginRequest(loginResult!!.accessToken)
                        preferenceHelper!!.putIsLogin(true)
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    }

                    override fun onCancel() {
                        // App code
                    }

                    override fun onError(exception: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")
                        Toast.makeText(this@HomeActivity,"Erreur de connexion", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        /*
        var accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = !(accessToken == null || accessToken.isExpired)
        accessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken,
                currentAccessToken: AccessToken
            ) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()

            }
        }*/

    }

    private fun graphLoginRequest(accessToken: AccessToken?) {
        val graphRequest = GraphRequest.newMeRequest(
            accessToken
        ) { jsonObject, _ ->
            try {
                preferenceHelper!!.putEmail(jsonObject.getString("name"))
                preferenceHelper!!.putEmail(jsonObject.getString("email"))
                //preferenceHelper!!.putIsLogin(true)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val bundle = Bundle()
        bundle.putString(
            "fields",
            "id,name,email"
        )
        graphRequest.parameters = bundle
        graphRequest.executeAsync()
    }

    private fun signInWithGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN )

    }

    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode  == RC_SIGN_IN)  {

                // The Task returned from this call is always completed, no need to attach
                // a listener.
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                /*Log.d(
                    "email", account!!.email
                )*/
                onLoggedIn(account!!)


            }catch (e: ApiException) {
                // Sign in was unsuccessful
                Log.e(
                    "failed code=", e.statusCode.toString()
                )
            }

                //onLoggedIn(account)

        }


    }
    /*override fun onDestroy() {
        super.onDestroy()
        accessTokenTracker.stopTracking()
    }*/


}