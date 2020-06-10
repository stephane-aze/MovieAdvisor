package com.masterprojet.films.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.masterprojet.films.HomeActivity
import com.masterprojet.films.MainActivity
import com.masterprojet.films.PreferenceHelper
import com.masterprojet.films.R


class SplashScreenActivity: AppCompatActivity() {
    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var mLogo : ImageView
    private val timeout: Long =2500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()
        setContentView(R.layout.splash_screen)
        init()
        animateLogo()
        redirectionActivity()

    }

    private fun redirectionActivity() {
        preferenceHelper = PreferenceHelper(this)

        Handler().postDelayed({
            if (!preferenceHelper.getIsLogin()) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()

            }
        }, timeout)

    }

    private fun init(){
        mLogo = findViewById(R.id.animLogo)
    }
    private fun animateLogo(){
        val animation: Animation= AnimationUtils.loadAnimation(this,
            R.anim.anim
        )
        mLogo.startAnimation(animation)
    }

}