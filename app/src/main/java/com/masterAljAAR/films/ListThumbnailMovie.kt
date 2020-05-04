package com.masterAljAAR.films

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.masterAljAAR.films.fragments.Movie

class ListThumbnailMovie (private val children : MutableList<Movie>)
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
        val path_image=child.poster_path
        val url =  "https://image.tmdb.org/t/p/w500$path_image"
        Glide.with(holder.itemView.context)
            .load(url)
            .placeholder(R.drawable.bobine_film)
            .into(holder.imageView)
        holder.textView.text = child.title
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textView : TextView = itemView.findViewById(R.id.thumbnail_title_movie)
        val imageView: ImageView = itemView.findViewById(R.id.thumbnail_image_movie)


    }
}