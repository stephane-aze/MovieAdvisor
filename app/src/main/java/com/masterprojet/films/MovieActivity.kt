package com.masterprojet.films

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.masterprojet.films.fragments.MessengerFragment
import com.masterprojet.films.ui.makeStatusBarTransparent
import kotlinx.android.synthetic.main.activity_movie.*


class MovieActivity:  AppCompatActivity(), RatingDialog.RatingDialogListener  {
    private var mTitleView: TextView? = null
    private var mDescriptionView: TextView? = null
    private var mButtonAddOpinion: Button? = null
    private var mNoteView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()
        setContentView(R.layout.activity_movie)
        val bundle = intent.extras
        init()
        getParams(bundle)
        btn_create_opinion.setOnClickListener { openDialog() }
        updateFragment(bundle)
    }

    private fun updateFragment(bundle: Bundle?) {
        val arguments = Bundle()
        arguments.putInt("movieId", bundle?.getInt("movieId")!!)

        val messengerFragment =
            MessengerFragment()
        messengerFragment.arguments = arguments
        supportFragmentManager.beginTransaction().add(
            R.id.frame_fragment, messengerFragment
        ).commit()
    }

    private fun getParams(bundle: Bundle?) {
        val title = bundle?.getString("title")
        val description = bundle?.getString("description")
        val note = bundle?.getString("vote")
        mTitleView?.text = title
        mDescriptionView?.text = description
        mNoteView?.text = note
        init()
        val url = "https://image.tmdb.org/t/p/w500${bundle?.getString("path")}"
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.bobine_film)
            .into(findViewById(R.id.movie_image))
    }


    private fun init() {
        mTitleView = findViewById(R.id.movie_title)
        mDescriptionView = findViewById(R.id.movie_description)
        mNoteView = findViewById(R.id.movie_note)
        mButtonAddOpinion = findViewById(R.id.btn_create_opinion)

    }

    private fun openDialog(){
        RatingDialog().run {
            show(supportFragmentManager,"dialog form avis")
        }
    }
    override fun applyRating(rating: Double, commentaire: String) {

    }

}