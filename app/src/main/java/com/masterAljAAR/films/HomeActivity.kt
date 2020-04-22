package com.masterAljAAR.films

import android.app.Activity
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
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity :  AppCompatActivity() {
    lateinit var loginBtn : Button
    lateinit var createUser : TextView
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var loginButtonFacebook : LoginButton
    lateinit var signInButton: SignInButton
    private lateinit var callbackManager: CallbackManager
    private val EMAIL = "email"
    private lateinit var accessTokenTracker: AccessTokenTracker
    private var btnTest: Button? = null
    private var preferenceHelper: PreferenceHelper? = null
    private val TAG = "AndroidClarified"
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            //onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }

        // updateUI(account)
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
            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, 101)
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
        loginButtonFacebook.run { setReadPermissions(listOf(EMAIL,"public_profile")) }

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    // App code
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })
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
        }

    }
    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            101 -> try {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                val task: com.google.android.gms.tasks.Task<GoogleSignInAccount>? =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                var account: GoogleSignInAccount? = task!!.getResult(ApiException::class.java)
                //onLoggedIn(account)
            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        accessTokenTracker.stopTracking()
    }


}