package com.masterprojet.films.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.masterprojet.films.model.MessagesViewModel
import com.masterprojet.films.ListMessages
import com.masterprojet.films.R
import kotlinx.android.synthetic.main.fragment_message.*

data class Message(val user_img: String, val user_name: String?, val rating: Double, val text: String, val dateTime: String,val movieId: Int)

class MessengerFragment : Fragment(){
    private lateinit var changeAdapter: ListMessages

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listMessageView.apply {
            layoutManager = LinearLayoutManager(activity)

            val arguments:Bundle? = arguments
            val dataMessagesManager=
                MessagesViewModel()
            var movieId=arguments?.getInt("movieId")
            Log.d("tag1",arguments?.getInt("movieId").toString())
            adapter =
                ListMessages(dataMessagesManager.getMessagesAll())

            movieId?.let { 
                adapter =
                ListMessages(dataMessagesManager.getMessages(it))
            }

        }

    }


}
