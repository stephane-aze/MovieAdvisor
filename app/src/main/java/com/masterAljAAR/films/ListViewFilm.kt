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


class ListViewFilm(private val listItem: MutableList<Movie>,val clickListener: (Movie) -> Unit) :
     SearchAdapter<Movie>(listItem) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = listItem[position]
        val path_image: String=movie.poster_path
        val url: String = "https://image.tmdb.org/t/p/w500$path_image"
        Glide.with(holder.itemView.context)
            .load(url)
            .placeholder(R.drawable.bobine_film)
            .into(holder.itemView.findViewById(R.id.list_image))
        holder.bind(movie,clickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = listItem.size


}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.mylist, parent, false)) {
    private var mTitleView: TextView? = null
    private var mDescriptionView: TextView? = null
    private var mNoteView: TextView? = null


    init {
        mTitleView = itemView.findViewById(R.id.list_title)
        mDescriptionView = itemView.findViewById(R.id.list_description)
        //mImageView = itemView.findViewById(R.id.list_image)
        mNoteView = itemView.findViewById(R.id.list_note)


    }

    fun bind(movie: Movie,clickListener: (Movie) -> Unit) {
        mTitleView?.text = movie.title
        mDescriptionView?.text = movie.overview
        itemView.setOnClickListener { clickListener(movie)}
        mNoteView?.text = movie.vote_average.toString()

    }

}