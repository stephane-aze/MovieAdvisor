package com.masterprojet.films.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.masterprojet.films.ListMovieByCategory
import com.masterprojet.films.R
import com.masterprojet.films.helpers.*
import com.masterprojet.films.model.Category
import com.masterprojet.films.model.Movie

import kotlinx.android.synthetic.main.fragment_category_movie_list.*




class CategoryMovieFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }
    private fun getDataCat1(): MutableList<Movie> {

        val googleJson = Gson()
        val listType = object : TypeToken<MutableList<Movie>>() {}.type
        return googleJson.fromJson(jsonCat1, listType)
    }
    private fun getDataCat2(): MutableList<Movie> {

        val googleJson = Gson()
        val listType = object : TypeToken<MutableList<Movie>>() {}.type
        return googleJson.fromJson(jsonCat2, listType)

    }
    private fun getDataCat3(): MutableList<Movie> {
        val googleJson = Gson()
        val listType = object : TypeToken<MutableList<Movie>>() {}.type
        return googleJson.fromJson(jsonCat3, listType)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_movie_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listMovieByCategoryView.apply {
            layoutManager = LinearLayoutManager(activity)
            val listMovieByCategory: MutableList<Category> = mutableListOf(
                Category("Plus populaires", getDataCat1()),
                Category("Dessins animés", getDataCat2()),
                Category("Autres", getDataCat3())

            )
            adapter =
                ListMovieByCategory(listMovieByCategory,context)

        }
    }

}
