package com.wahyu.hiringapps2.dashboard.profile.editProfile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivityFormEditProfileBinding
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.Key
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityFormEditProfileBinding
    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var viewModel: EditProfileViewModel
    private fun getPhotoImage(file: String) : String = "http://34.234.66.114:8080/uploads/$file"

    companion object {
        // image pick code
        private const val IMAGE_PICK_CODE = 1000
        // permission code
        private const val PERMISSION_CODE = 1001
    }

    override fun initView() {
        this.setSupportActionBar(binding.topToolbar)
    }

    override fun initListener() {
        binding.buttonCancel.setOnClickListener {
            onBackPressed()
        }
        binding.tvEditPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    // permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    // show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    // permission already granted
                    pickImageFromGallery()
                }
            } else pickImageFromGallery()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_edit_profile)
        sharedPref = SharedPreferencesUtil(this)

        val service = ApiClient.getApiClient(this)?.create(EditProfileApiService::class.java)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        viewModel.setSharedPref(sharedPref)
        if (service != null) viewModel.setEditProfileService(service)
        viewModel.callGetProfileDataApi()
        subscribeLiveData()

        initView()
        initListener()
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.isEditProfileLiveData.observe(this, {
            if (it) {
                val data = viewModel.listLiveData.value?.firstOrNull()
                binding.tvName.text = data?.name ?: "Full Name"
                if (data?.profileImage.isNullOrEmpty()) {
                    binding.imageProfile.setImageResource(R.drawable.ic_person)
                } else {
                    Picasso.get().load(getPhotoImage(data?.profileImage!!)).into(binding.imageProfile)
                }
                binding.tvJobTitle.text = data?.roleJob ?: "Role Job"
                binding.tvCity.text = data?.city ?: "City"
                binding.tvDescription.text = data?.description ?: "Description"
                binding.etInputInstagram.text = data?.instagramLink?.toEditable() ?: "Instagram Link".toEditable()
                binding.etInputLinkedin.text = data?.linkedinLink?.toEditable() ?: "Linkedin Link".toEditable()

            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission from popup granted
                    pickImageFromGallery()
                } else {
                    // permission from popup denied
                    Toast.makeText(this, "Permission denied !", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.imageProfile.setImageURI(data?.data)

            val file = File(data?.data?.path!!)

            var img: MultipartBody.Part? = null
            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = contentResolver.openInputStream(data.data!!)
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)

            val userId = sharedPref.getInt(Key.PREF_USER_ID)!!
            val companyField = createPartFromString(binding.etInputCompanyField.text.toString())
            val city = createPartFromString(binding.etInputCity.text.toString())
            val description = createPartFromString(binding.etInputDescription.text.toString())
            val instagram = createPartFromString(binding.etInputInstagram.text.toString())
            val linkedin = createPartFromString(binding.etInputLinkedin.text.toString())
            val name = createPartFromString("Wahyu Kurniawan")
            val email = createPartFromString("wahyukurniawaan@gmail.coma")
            val password = createPartFromString("password")
            val company = createPartFromString("Arkademy")
            val roleJob = createPartFromString("CEO")
            val phoneNumber = createPartFromString("089630033462")

            img = reqFile?.let { it ->
                MultipartBody.Part.createFormData("image", file.name, it)
            }

                binding.buttonSave.setOnClickListener {
                    viewModel.callEditProfileDataApi(userId, companyField, city, description, instagram, linkedin, img!!, name, email,
                        password, company, roleJob, phoneNumber)
                    Toast.makeText(this, "Berhasil input data", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }

    @NonNull
    private fun createPartFromString(json: String) : RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json.toRequestBody(mediaType)
    }
}