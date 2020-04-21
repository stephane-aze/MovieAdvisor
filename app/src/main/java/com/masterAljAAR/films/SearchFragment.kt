package com.masterAljAAR.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.android.synthetic.main.fragment_search.*

data class Movie(val title: String, val description: String, val note: Double, val image: String )

class SearchFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView

    private val nicCageMovies = listOf(
        Movie("Star Wars VIII - Les derniers Jedi",
            "Nouvel épisode de la saga. Les héros du Réveil de la force rejoignent les figures légendaires de la galaxie dans une aventure épique qui...",
            7.2,""),
        Movie("La Guerre des étoiles",
            "Il y a bien longtemps, dans une galaxie très lointaine... La guerre civile fait rage entre l'Empire galactique et l'Alliance rebelle...",
            8.1, "")

    )
     fun getData(): JSONArray {
       val jsonArray: JSONArray = JSONArray("""[
        {
          id:181808,
          vote_average:7.2,
          title:"Star Wars VIII - Les derniers Jedi",
          poster_path:"",
          original_title:"Star Wars: The Last Jedi",
          overview:"Nouvel épisode de la saga. Les héros du Réveil de la force rejoignent les figures légendaires de la galaxie dans une aventure épique qui révèle des secrets ancestraux sur la Force et entraîne de choquantes révélations sur le passé…",
          release_date:"2017-12-13"
        },
        {
          id:181809,
          vote_average:8.1,
          title:"La Guerre des étoiles",
          poster_path:"",
          original_title:"Star Wars",
          overview:"Il y a bien longtemps, dans une galaxie très lointaine... La guerre civile fait rage entre l'Empire galactique et l'Alliance rebelle. Capturée par les troupes de choc de l'Empereur menées par le sombre et impitoyable Dark Vador, la princesse Leia Organa dissimule les plans de l’Étoile Noire, une station spatiale invulnérable, à son droïde R2-D2 avec pour mission de les remettre au Jedi Obi-Wan Kenobi. Accompagné de son fidèle compagnon, le droïde de protocole C-3PO, R2-D2 s'échoue sur la planète Tatooine et termine sa quête chez le jeune Luke Skywalker. Rêvant de devenir pilote mais confiné aux travaux de la ferme, ce dernier se lance à la recherche de ce mystérieux Obi-Wan Kenobi, devenu ermite au cœur des montagnes désertiques de Tatooine...",
          release_date:"1977-05-25"
        }
    ]""")
       return jsonArray
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
   return inflater.inflate(R.layout.fragment_search, container, false)

}
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
   super.onViewCreated(view, savedInstanceState)
   listMoviesView.apply {
       layoutManager = LinearLayoutManager(activity)
       adapter = ListViewFilm(nicCageMovies)
   }
}

companion object {
   fun newInstance(): SearchFragment = SearchFragment()
}
}
