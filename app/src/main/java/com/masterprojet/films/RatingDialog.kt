package com.masterprojet.films

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class RatingDialog: DialogFragment() {
    private lateinit var commentView: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var mRatingScale: TextView
    private lateinit var listener: RatingDialogListener


    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.dialog_avis, null)
            commentView = view.findViewById(R.id.comment)
            ratingBar = view.findViewById(R.id.eval_user)
            mRatingScale = view.findViewById(R.id.ratingScale)

            eventRatingBar()
            builder.setView(view)
                .setTitle("Ajouter votre avis")
                // Add action buttons
                .setPositiveButton(R.string.send)
                { _, _ ->
                    val comment = commentView.text.toString()
                    val rating = ratingBar.rating.toDouble()
                    listener.applyRating(rating, comment)

                }
                .setNegativeButton(R.string.cancel)
                { _, _ ->
                    dialog!!.cancel()
                }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")    }


    @SuppressLint("SetTextI18n")
    private fun eventRatingBar() {
        ratingBar.setOnRatingBarChangeListener { ratingBar, v, _ ->
            mRatingScale.text=v.toString()
            when (ratingBar.rating.toInt()) {
                1 -> mRatingScale.text="Très mauvais"
                2 -> mRatingScale.text="Un peu mauvais"
                3 -> mRatingScale.text="Passable"
                4 -> mRatingScale.text="Bien"
                5 -> mRatingScale.text="Très bien"
                else -> mRatingScale.text=""
            }
        }
    }
    interface RatingDialogListener {
        fun applyRating(rating: Double, commentaire: String)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as RatingDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement RatingDialogListener")
            )
        }
    }
}