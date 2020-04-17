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

    var maintitle = arrayOf(
        "Title 1", "Title 2",
        "Title 3", "Title 4",
        "Title 5"
    )

    var subtitle = arrayOf(
        "Sub Title 1", "Sub Title 2",
        "Sub Title 3", "Sub Title 4",
        "Sub Title 5"
    )

    var imgid = arrayOf<Int>(

    )
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
            item->when(item.itemId){
                R.id.app_bar_search -> {
                    replaceFragment(SearchFragment())
                    println("Ok Search")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.messages -> {
                    println("Ok feefe")
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

val adapter = ListViewFilm(this, maintitle, subtitle, imgid)
list = findViewById<View>(R.id.list) as ListView
list.adapter = adapter


list.onItemClickListener =
    OnItemClickListener { parent, view, position, id ->
        // TODO Auto-generated method stub
        if (position == 0) {
            //code specific to first list item
            Toast.makeText(
                applicationContext,
                "Place Your First Option Code",
                Toast.LENGTH_SHORT
            ).show()
        } else if (position == 1) {
            //code specific to 2nd list item
            Toast.makeText(
                applicationContext,
                "Place Your Second Option Code",
                Toast.LENGTH_SHORT
            ).show()
        } else if (position == 2) {
            Toast.makeText(
                applicationContext,
                "Place Your Third Option Code",
                Toast.LENGTH_SHORT
            ).show()
        } else if (position == 3) {
            Toast.makeText(
                applicationContext,
                "Place Your Forth Option Code",
                Toast.LENGTH_SHORT
            ).show()
        } else if (position == 4) {
            Toast.makeText(
                applicationContext,
                "Place Your Fifth Option Code",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
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
            preferenceHelper.putIsLogin(false)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()

        true
    }
    else -> super.onOptionsItemSelected(item)
}
}/**/
/*override fun logout(menu: Menu?): Boolean{
return false
}*/

}
