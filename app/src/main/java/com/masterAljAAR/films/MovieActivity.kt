package com.masterAljAAR.films

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie.*
import com.masterAljAAR.films.fragments.MessengerFragment
import com.masterAljAAR.films.model.MessagesViewModel

class MovieActivity:  AppCompatActivity(), RatingDialog.RatingDialogListener  {
    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var dataMessagesManager: MessagesViewModel
    private var mTitleView: TextView? = null
    private var mDescriptionView: TextView? = null
    private var mButtonAddOpinion: Button? = null
    private var mNoteView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        val bundle: Bundle? = intent.extras
        val path: String? = bundle?.getString("path")
        val title: String? = bundle?.getString("title")
        val description: String? = bundle?.getString("description")
        val note: String? = bundle?.getString("vote")

        mTitleView = findViewById(R.id.movie_title)
        mDescriptionView = findViewById(R.id.movie_description)
        mNoteView = findViewById(R.id.movie_note)
        mButtonAddOpinion = findViewById(R.id.btn_create_opinion)
        mTitleView?.text = title
        mDescriptionView?.text = description
        mNoteView?.text = note
        val url: String = "https://image.tmdb.org/t/p/w500$path"
        preferenceHelper = PreferenceHelper(this)
        Glide.with(this)
            .load(url)
            //.override(200,100)
            //.centerCrop()
            .placeholder(R.drawable.bobine_film)
            .into(findViewById(R.id.movie_image))
        btn_create_opinion.setOnClickListener { openDialog() }
        val messengerFragment =
            MessengerFragment()
        supportFragmentManager.beginTransaction().add(
            R.id.frame_fragment, messengerFragment).commit()/**/
    }
    private fun openDialog(){
        RatingDialog().run {
            show(supportFragmentManager,"dialog form avis")
        }
    }
    override fun applyRating(rating: Double, commentaire: String) {
        val newMessage = com.masterAljAAR.films.fragments.Message(
            "",
            preferenceHelper.getNames(),
            rating,
            commentaire,
            "01/05/2020"
        )
        val messengerFragment =
            MessengerFragment()
        messengerFragment.changeList(newMessage)
        supportFragmentManager.beginTransaction().replace(
            R.id.frame_fragment, messengerFragment,"dff").commit()
    }

}