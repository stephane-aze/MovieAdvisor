package com.masterAljAAR.films

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


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
 R.id.menu_search -> {

     var mSearchView : SearchView=item.actionView as SearchView

     mSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
         override fun onQueryTextChange(newText: String?): Boolean {
             Log.i("TAG","Llego al querysubmit")
             return true
         }

         override fun onQueryTextSubmit(query: String?): Boolean {
             Log.i("TAG","Llego al querytextchange")
             return false
         }
     })
     return true

     }
 else -> super.onOptionsItemSelected(item)
}
}/**/
private fun logout() {

preferenceHelper.putIsLogin(false)
     val intent = Intent(applicationContext, HomeActivity::class.java)
     startActivity(intent)
     finish()

}

}
