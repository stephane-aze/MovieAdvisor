package com.masterAljAAR.films
import android.app.Activity
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject


class ListViewFilm(private val list: List<Movie>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = list[position]
        val url: String = "https://image.tmdb.org/t/p/w500"+movie.poster_path
        Glide.with(holder.itemView.context)
            .load(url)
            .placeholder(R.drawable.bobine_film)
            .into(holder.itemView.findViewById(R.id.list_image))
        holder.bind(movie)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = list.size


}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.mylist, parent, false)) {
    private var mTitleView: TextView? = null
    private var mDescriptionView: TextView? = null
    private var mImageView: TextView? = null
    private var mNoteView: TextView? = null


    init {
        mTitleView = itemView.findViewById(R.id.list_title)
        mDescriptionView = itemView.findViewById(R.id.list_description)
        //mImageView = itemView.findViewById(R.id.list_image)
        mNoteView = itemView.findViewById(R.id.list_note)


    }

    fun bind(movie: Movie) {
        mTitleView?.text = movie.title
        mDescriptionView?.text = movie.overview
        //mImageView?.text = movie.image
        val url: String = "https://image.tmdb.org/t/p/w500"+movie.poster_path
        /*Glide.with(movie.poster_path)
            .load(url)
            .placeholder(R.drawable.bobine_film)
            .override(100,100)
            .into(R.id.list_image)*/
        mNoteView?.text = movie.vote_average.toString()

    }

}
