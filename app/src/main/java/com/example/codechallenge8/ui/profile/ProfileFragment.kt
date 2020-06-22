package com.example.codechallenge8.ui.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.codechallenge8.R
import com.example.codechallenge8.model.UsersResponse
import com.example.codechallenge8.util.SharedPrefManager
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.android.ext.android.inject
import java.io.File

const val MY_PERMISSION = 200

class ProfileFragment : Fragment(), InterfaceProfile.View {

    private val dialog: AlertDialog by lazy {
        SpotsDialog.Builder().setContext(activity).build()
    }

    private val presenter by inject<ProfilePresenter>()
    private var path = ""

    companion object {
        val TAG = ProfileFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val putUser = activity?.let { SharedPrefManager.getInstance(it).putUser}
        val token = putUser?.token
        val tokens = "Bearer ".plus(token)
        Log.d(TAG, tokens)

        btn_click.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(requireContext(), this)
        }

        btn_simpan.setOnClickListener {


            val email = et_email_profile.text.toString()
            val username = et_username_profile.text.toString()
            val imageFile = File(path)
            val requestImage = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImage)

            presenter.upload(tokens, filePart, username, email)
            Log.d(TAG, filePart.toString())
            Log.d(TAG, username)
            Log.d(TAG, email)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.view = this
        requestPermission()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = CropImage.getActivityResult(data)
        if (resultCode == RESULT_OK){
            when(requestCode){
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val resultUri: Uri = result.uri
                    path = resultUri.path!!
                    //img_profile.setImageURI(resultUri)
                    Glide.with(activity?.baseContext!!).load(resultUri).into(img_profile)
                }
            }
        }

    }

    private fun requestPermission(): Boolean {
        val cameraPermission =
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED

        val writeExtStorage =
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED

        val readExtStorage =
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED

        if (cameraPermission && writeExtStorage && readExtStorage){
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), MY_PERMISSION
                )
            }

        } else {
            return true
        }
        return false
    }

    override fun onSuccessUpdateUser(msg: UsersResponse) {
        super.onSuccessUpdateUser(msg)
        activity?.let { SharedPrefManager.getInstance(it).updateUser(msg) }
    }

    override fun hideLoading() {
        if (dialog.isShowing) dialog.dismiss()
    }

    override fun showLoading() {
        if (!dialog.isShowing) dialog.show()
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onError(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}