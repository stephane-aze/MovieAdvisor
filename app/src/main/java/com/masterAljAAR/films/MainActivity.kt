package com.masterAljAAR.films



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var isStarted = false
    var progressStatus = 0
    var handler: Handler? = null
    var secondaryHandler: Handler? = Handler()
    var primaryProgressStatus = 0
    var secondaryProgressStatus = 0
    lateinit var loginBtn : Button
    lateinit var list: ListView
    private lateinit var preferenceHelper: PreferenceHelper

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
            item->when(item.itemId){
                R.id.app_bar_search -> {
                    replaceFragment(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.messages -> {
                    println("Ok feefe")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profiles -> {
                    replaceFragment(ProfilesFragment())
                    return@OnNavigationItemSelectedListener true
                }

            }
        return@OnNavigationItemSelectedListener false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        preferenceHelper = PreferenceHelper(this)

        if (!preferenceHelper.getIsLogin()) {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()/**/
        }
     /**/

    /*handler = Handler(Handler.Callback {
        if (isStarted) {
            progressStatus++
        }
        progressBarHorizontal.progress = progressStatus
        textViewHorizontalProgress.text = "${progressStatus}/${progressBarHorizontal.max}"
        handler?.sendEmptyMessageDelayed(0, 100)

        true
    })

    handler?.sendEmptyMessage(0)*/
            replaceFragment(SearchFragment())

    }
    private fun replaceFragment(fragment: Fragment){
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragmentContainer,fragment)
    fragmentTransaction.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(
        R.menu.main_menu,
        menu
    )
    return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                    logout()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }/**/
     fun logout() {

        preferenceHelper!!.putIsLogin(false)
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()

    }

}
