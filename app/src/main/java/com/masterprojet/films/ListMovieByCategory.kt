package com.masterprojet.films

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.masterprojet.films.model.Category
import com.masterprojet.films.model.Movie

import kotlinx.android.synthetic.main.list_movie_by_category.view.*

class ListMovieByCategory(private val listItemByCategory: MutableList<Category>, val context: Context): RecyclerView.Adapter<CategoryViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    private val mContext = context
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CategoryViewHolder{
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_movie_by_category,parent,false)
        return CategoryViewHolder(inflater)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category: Category = listItemByCategory[position]
        holder.textView.text = category.title
        val childLayoutManager = LinearLayoutManager(holder.recyclerView.context, RecyclerView.HORIZONTAL, false)
        childLayoutManager.initialPrefetchItemCount = 4
        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = ListThumbnailMovie(category.listMovie,context){ movieItem: Movie ->
                partItemClicked(movieItem)
            }
            setRecycledViewPool(viewPool)
        }/**/
    }
    private fun partItemClicked(movieItem : Movie) {
        val intent = Intent(mContext, MovieActivity::class.java)
        intent.putExtra("path",movieItem.poster_path)
        intent.putExtra("title",movieItem.title)
        intent.putExtra("description",movieItem.overview)
        intent.putExtra("vote",movieItem.vote_average.toString())
        intent.putExtra("movieId",movieItem.id)
        mContext.startActivity(intent)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = listItemByCategory.size

}
class CategoryViewHolder(itemView : View) :
    RecyclerView.ViewHolder(itemView) {
        val recyclerView : RecyclerView = itemView.listMovieForCategory
        val textView:TextView = itemView.title_category
    }
