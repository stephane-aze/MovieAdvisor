package com.masterAljAAR.films

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MovieActivity:  AppCompatActivity()  {
    private var mTitleView: TextView? = null
    private var mDescriptionView: TextView? = null
    private var mImageView: ImageView? = null
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
        mTitleView?.text = title
        mDescriptionView?.text = description
        mNoteView?.text = note
        val url: String = "https://image.tmdb.org/t/p/w500$path"

        Glide.with(this)
            .load(url)
            //.override(200,100)
            //.centerCrop()
            .placeholder(R.drawable.bobine_film)
            .into(findViewById(R.id.movie_image))

    }
}