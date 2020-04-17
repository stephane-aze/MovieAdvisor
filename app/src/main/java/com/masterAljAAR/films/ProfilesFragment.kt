package com.masterAljAAR.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


class ProfilesFragment: Fragment() {
    val GOOGLE_ACCOUNT = "google_account"
    private val profileName: TextView? = null
    private  var profileEmail:TextView? = null
    private val profileImage: ImageView? = null
    private val signOut: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.profiles_view, container, false)
    }
}