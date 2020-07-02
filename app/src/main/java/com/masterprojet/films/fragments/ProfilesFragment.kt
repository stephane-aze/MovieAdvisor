package com.masterprojet.films.fragments

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.camera.core.ImageCapture
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.masterprojet.films.HomeActivity
import com.masterprojet.films.R
import com.masterprojet.films.databinding.ProfilesViewBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.edit_profile_dialog.view.*
import kotlinx.android.synthetic.main.profiles_view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*



private const val WRITE_EXT_STORAGE_REQUEST_CODE = 123
class ProfilesFragment: Fragment() {
    private val REQUEST_IMAGE_CAPTURE =100
    private val REQUEST_TAKE_PHOTO = 1
    private val PICK_IMAGE_REQUEST = 71
    val GOOGLE_ACCOUNT = "google_account"
    private lateinit var packageManager: PackageManager
    private lateinit var currentPhotoPath: String
    private lateinit var contextProfiles: Context
    private lateinit var binding: ProfilesViewBinding
    private lateinit var auth: FirebaseAuth
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profiles_view, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfilesViewBinding.bind(view)
        init()
        setDataOnView()
        binding.uploadImage.setOnClickListener {
            selectImage()
        }
        binding.btnEditProfile.setOnClickListener {
            editProfile()
        }
        binding.signOut.setOnClickListener { logout() }

    }
    private fun init(){
        auth = Firebase.auth
        contextProfiles = context!!
        packageManager = contextProfiles.packageManager
    }
    private fun setDataOnView() {
        val currentUser = auth.currentUser
        var mPhotoUrl:String? = null
        currentUser?.run {
            // Name, email address, and profile photo Url

            mPhotoUrl = photoUrl.toString().plus("?height=500")
            binding.profileName.text = displayName
            binding.profileEmail.text = email
            Log.d("URI",uid)
        }
        Glide.with(this)
            .load(mPhotoUrl)
            .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
            .circleCrop()
            .into(binding.profileImage)

    }

    private fun editProfile() {
        showDialog()

    }

    private fun showDialog() {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.edit_profile_dialog,null)
        val editTextProfilesSex: EditText = view.findViewById(R.id.editTextProfilesSex)
        val editTextProfilesName: EditText= view.findViewById(R.id.editTextProfilesName)
        val editTextProfilesEmail: EditText = view.findViewById(R.id.editTextProfilesEmail)
        val builder = AlertDialog.Builder(contextProfiles)
            .setView(view)
            .setTitle("Modification")

        val  mAlertDialog = builder.show()
        view.btn_send_edit_user.setOnClickListener {
            val sex = editTextProfilesSex.text.toString()
            val name = editTextProfilesName.text.toString()
            val email = editTextProfilesEmail.text.toString()
            mAlertDialog.dismiss()

        }
        view.btn_cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }

    }


    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder =AlertDialog.Builder(context)
        builder.setTitle("Ajouter une Photo!")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    if(hasCamera()){
                        managePermission()

                    }
                }
                options[item] == "Choose from Gallery" -> {
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, 2)
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun hasCamera():Boolean= packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)

    private fun dispatchTakePictureIntent() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()

                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.d("TAKE_PHOTO",ex.toString())
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        contextProfiles,
                        "com.masterAljAAR.films.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            /*val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profileImage.setImageBitmap(imageBitmap)*/
            //Log.d("AZEROT",image_uri.toString())
            Glide.with(this)
                .load(image_uri.toString())
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .circleCrop()
                .into(binding.profileImage)
        }
    }
    @Throws(IOException::class)
    private fun createImageFile(): File {
        Log.d("hello","Hey!!!!")
        // Create an image file name
        val timeStamp: String = SimpleDateFormat(getString(R.string.simple_date_format)).format(Date())
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            contextProfiles.getExternalFilesDir(Environment.DIRECTORY_PICTURES) /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
    private fun managePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(contextProfiles,CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(contextProfiles,WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(CAMERA,WRITE_EXTERNAL_STORAGE)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
            }
            else{
                //permission already granted
                openCamera()
            }
        }
        else{
            //system os is < marshmallow
            openCamera()
        }
    }
    private fun logout() {

        auth.signOut()
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(intent)
        LoginManager.getInstance().logOut()

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(contextProfiles, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    private fun openCamera() {
        Log.d("AZE","kamé")
        val values = ContentValues()
        Log.d("AZE","kaméha")

        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        Log.d("AZE","kaméhamé")

        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        Log.d("AZE","kaméhaméhamé")

        image_uri = activity!!.contentResolver!!.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        Log.d("AZE","kaméhaméhaméha")
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        Log.d("AZE","kaméhaméhaméhamé")

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        Log.d("AZE","kaméhaméhaméhaméyyaaa")

        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }


}