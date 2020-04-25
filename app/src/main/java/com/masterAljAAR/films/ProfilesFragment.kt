package com.masterAljAAR.films

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.squareup.picasso.Picasso


class ProfilesFragment: Fragment() {
    val GOOGLE_ACCOUNT = "google_account"
    private var profileName: TextView? = null
    private  var profileEmail:TextView? = null
    private var profileImage: ImageView? = null
    private var signOut: Button? = null
    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        preferenceHelper = PreferenceHelper(requireContext())
        return inflater.inflate(R.layout.profiles_view, container, false)
    }
    private fun init(view: View){
        profileName = view.findViewById(R.id.profile_name)
        profileEmail = view.findViewById(R.id.profile_email)
        profileImage = view.findViewById(R.id.profile_image)
        signOut = view.findViewById(R.id.sign_out)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setDataOnView()
        signOut!!.setOnClickListener { logout() }

    }
    private fun setDataOnView() {
        var acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(activity)
        if(acct!=null){
            Picasso.get().load(acct.getPhotoUrl()).centerInside().fit()
                .into(profileImage)
            profileName!!.text = acct.getDisplayName()
            profileEmail!!.text = acct.getEmail()
        }else if(preferenceHelper!!.getIsLogin()){
            Glide.with(this)
                .load("")
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .into(profileImage!!)
            profileName!!.text = preferenceHelper!!.getNames()
            profileEmail!!.text = preferenceHelper!!.getEmail()
        }
    }
    private fun logout() {

        preferenceHelper!!.putIsLogin(false)
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(intent)

    }

}