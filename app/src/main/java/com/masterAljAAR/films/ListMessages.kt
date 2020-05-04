package com.masterAljAAR.films

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.masterAljAAR.films.fragments.Message

class ListMessages(private val listItem: MutableList<Message>): RecyclerView.Adapter<MessagesViewHolder>() {

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val message: Message = listItem[position]

        holder.bind(message)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MessagesViewHolder(inflater,parent)
    }
    override fun getItemCount(): Int= listItem.size


}
class MessagesViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_messages, parent, false)){
    private var usernameView: TextView? = null
    private var messageView: TextView? = null
    private var ratingBar: RatingBar? = null
    init {
        usernameView  = itemView.findViewById(R.id.username_message)
        messageView = itemView.findViewById(R.id.text_message)
        ratingBar = itemView.findViewById(R.id.ratingBar2)


    }

    fun bind(message: Message) {
        usernameView?.text = message.user_name
        messageView?.text = message.text
        ratingBar?.rating = message.rating.toFloat()

    }

}