package com.masterprojet.films

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.masterprojet.films.model.Movie


class ListThumbnailMovie (private val children : MutableList<Movie>, val context: Context, private val clickListener: (Movie) -> Unit)
    : RecyclerView.Adapter<ListThumbnailMovie.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {

        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.thumbnail_movie,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        val child = children[position]
        holder.bind(child,clickListener)
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        private val textView : TextView = itemView.findViewById(R.id.thumbnail_title_movie)
        private val imageView: ImageView = itemView.findViewById(R.id.thumbnail_image_movie)

        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            textView.text = movie.title
            val url =  "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            Glide.with(itemView)
                .load(url)
                .placeholder(R.drawable.bobine_film)
                .into(imageView)

            itemView.setOnClickListener { clickListener(movie)}
            itemView.setOnTouchListener { v, _ ->
                val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                v.startAnimation(animation)
                false
            }
        }

    }
}