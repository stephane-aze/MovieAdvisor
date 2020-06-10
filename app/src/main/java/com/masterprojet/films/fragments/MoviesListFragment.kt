package com.masterprojet.films.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.masterprojet.films.ListViewFilm
import com.masterprojet.films.MovieActivity
import com.masterprojet.films.R
import com.masterprojet.films.SearchAdapter
import kotlinx.android.synthetic.main.fragment_movies_list.*
import java.util.*
import com.masterprojet.films.helpers.*


data class Movie(val id:Int,val title: String, val overview: String, val vote_average: Double, val poster_path: String ):
    SearchAdapter.Searchable {
    override fun getSearchCriteria(): String {
        return title.toLowerCase(Locale.ROOT)
    }
}

class SearchFragment : Fragment() {

    private lateinit var searchMovieView: SearchView
    private lateinit var searchAdapter: ListViewFilm


    private fun getData(): MutableList<Movie> {
        val googleJson =  Gson()
        val listType = object : TypeToken<MutableList<Movie>>() { }.type
        return googleJson.fromJson(jsonMovieList, listType)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       retainInstance = true

    }
    override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_movies_list, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
       listMoviesView.apply {
           layoutManager = LinearLayoutManager(activity)
           adapter =
               ListViewFilm(getData()) { movieItem: Movie ->
                   partItemClicked(movieItem)
               }

       }
        searchAdapter =
            ListViewFilm(getData()) { movieItem: Movie ->
                partItemClicked(movieItem)
            }
        searchMovie(view)

    }

    private fun searchMovie(view: View) {

        searchMovieView=view.findViewById(R.id.search_movie)
        searchMovieView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return false
            }

        })


    }

    private fun partItemClicked(movieItem : Movie) {
        val intent = Intent(context, MovieActivity::class.java)
        intent.putExtra("path",movieItem.poster_path)
        intent.putExtra("title",movieItem.title)
        intent.putExtra("description",movieItem.overview)
        intent.putExtra("vote",movieItem.vote_average.toString())
        intent.putExtra("movieId",movieItem.id)
        startActivity(intent)
    }
    private fun search(s: String?) {
        searchAdapter.search(s) {
            // update UI on nothing found
            Toast.makeText(context, "Aucun résultat", Toast.LENGTH_SHORT).show()
        }
        listMoviesView.adapter=searchAdapter

    }

}
