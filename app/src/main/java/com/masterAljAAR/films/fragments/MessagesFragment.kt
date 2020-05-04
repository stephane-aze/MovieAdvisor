package com.masterAljAAR.films.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.masterAljAAR.films.model.MessagesViewModel
import com.masterAljAAR.films.ListMessages
import com.masterAljAAR.films.R
import kotlinx.android.synthetic.main.fragment_message.*

data class Message(val user_img: String?, val user_name: String?, val rating: Double, val text: String, val dateTime: String) {

}

class MessengerFragment : Fragment(){
    lateinit var changeAdapter: ListMessages

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
            val dataMessagesManager=
                MessagesViewModel()
            adapter =
                ListMessages(dataMessagesManager.getMessages())

        }

    }
    fun changeList(message: Message?){
        val dataMessagesManager= MessagesViewModel()
        val listof= mutableListOf<Message>()
        //listof.addAll(dataMessagesManager.getMessages())
        listof.add(message!!)
        changeAdapter= ListMessages(listof)
        listMessageView.adapter =changeAdapter
        Log.d("tag",changeAdapter.toString())
    }


}
