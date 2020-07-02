package com.masterprojet.films
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.masterprojet.films.model.Movie


class ListViewFilm(private val listItem: MutableList<Movie>, private val clickListener: (Movie) -> Unit) :
     SearchAdapter<Movie>(listItem) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = listItem[position]
        holder.bind(movie,clickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = listItem.size


}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_movie, parent, false)) {
    private var mTitleView: TextView = itemView.findViewById(R.id.list_title)
    private var mDescriptionView: TextView = itemView.findViewById(R.id.list_description)
    private var mNoteView: TextView= itemView.findViewById(R.id.list_note)
    private var mImageView: ImageView= itemView.findViewById(R.id.list_image)




    fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
        mTitleView.text = movie.title
        mDescriptionView.text = movie.overview
        itemView.setOnClickListener { clickListener(movie)}
        mNoteView.text = movie.vote_average.toString()
        val url = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
        Glide.with(itemView.context)
            .load(url)
            .placeholder(R.drawable.bobine_film)
            .into(mImageView)


    }

}