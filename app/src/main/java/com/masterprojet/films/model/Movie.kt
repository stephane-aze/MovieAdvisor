package com.masterprojet.films.model

import com.masterprojet.films.SearchAdapter
import java.util.*

data class Movie(val id:Int,
                 val title: String,
                 val overview: String,
                 val vote_average: Double,
                 val poster_path: String,
                 val vote_count:Double,
                 val popularity: Double )
    :
    SearchAdapter.Searchable {
    override fun getSearchCriteria(): String {
        return title.toLowerCase(Locale.ROOT)
    }
}