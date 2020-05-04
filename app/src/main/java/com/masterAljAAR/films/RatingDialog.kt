package com.masterAljAAR.films

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class RatingDialog: DialogFragment() {
    lateinit var commentView: EditText
    lateinit var ratingBar: RatingBar
    lateinit var mRatingScale: TextView
    internal lateinit var listener: RatingDialogListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val view: View = inflater.inflate(R.layout.dialog_avis, null)
            commentView = view.findViewById(R.id.comment)
            ratingBar = view.findViewById(R.id.eval_user)
            mRatingScale = view.findViewById(R.id.ratingScale)

            eventRatingBar()
            builder.setView(view)
                .setTitle("Ajouter votre avis")
                // Add action buttons
                .setPositiveButton(R.string.send)
                { dialog, id ->
                    val commentaire = commentView.text.toString()
                    val rating = ratingBar.rating.toDouble()
                    listener.applyRating(rating, commentaire)

                }
                .setNegativeButton(R.string.cancel)
                { dialog, id ->
                    getDialog()!!.cancel()
                }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")    }

    private fun eventRatingBar() {
        ratingBar.setOnRatingBarChangeListener { ratingBar, v, b ->
            mRatingScale.setText(v.toString())
            when (ratingBar.rating.toInt()) {
                1 -> mRatingScale.setText("Très mauvais")
                2 -> mRatingScale.setText("Un peu mauvais")
                3 -> mRatingScale.setText("Passable")
                4 -> mRatingScale.setText("Bien")
                5 -> mRatingScale.setText("Très bien")
                else -> mRatingScale.setText("")
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