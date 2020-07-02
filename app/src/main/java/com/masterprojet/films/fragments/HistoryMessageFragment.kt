package com.masterprojet.films.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.masterprojet.films.ListMessages
import com.masterprojet.films.R
import com.masterprojet.films.model.MessagesViewModel
import kotlinx.android.synthetic.main.fragment_message.*

class HistoryMessageFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recyclerview_message, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listMessageView.apply {
            layoutManager = LinearLayoutManager(activity)

            val dataMessagesManager=
                MessagesViewModel()
            adapter =
                ListMessages(dataMessagesManager.getMessagesAll())

        }

    }
}