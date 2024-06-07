package com.example.rentalexpertz.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.rentalexpertz.ApiHelper.ApiController
import com.example.rentalexpertz.ApiHelper.ApiResponseListner
import com.example.rentalexpertz.Model.ProfileBean
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.SalesApp
import com.example.rentalexpertz.Utills.Utility
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RCORDeActivity : AppCompatActivity(), ApiResponseListner {
    private lateinit var apiClient: ApiController
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private var permissionToRecordAccepted = false
    private val permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private var mediaRecorder: MediaRecorder? = null
    private lateinit var outputFilePath: String

    private lateinit var startRecordingButton: Button
    private lateinit var stopRecordingButton: Button
    private lateinit var uploadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rcorde)

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        startRecordingButton = findViewById(R.id.startRecordingButton)
        stopRecordingButton = findViewById(R.id.stopRecordingButton)
        uploadButton = findViewById(R.id.uploadButton)

        startRecordingButton.setOnClickListener {
            setupMediaRecorder()
            startRecording()
            startRecordingButton.isEnabled = false
            stopRecordingButton.isEnabled = true
        }

        stopRecordingButton.setOnClickListener {
            stopRecording()
            startRecordingButton.isEnabled = true
            stopRecordingButton.isEnabled = false
        }

        uploadButton.setOnClickListener {
            uploadAudioFile("19")
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    private fun setupMediaRecorder() {
        outputFilePath = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(outputFilePath)
            prepare()
        }
    }

    private fun startRecording() {
        mediaRecorder?.start()
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }


    fun uploadAudioFile(leadID: String?) {
        val file = File(outputFilePath)
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("lead_id",leadID!!)
        //  builder.addFormDataPart("voice_notes","4433123")
        //val requestBody = RequestBody.create("audio/*".toMediaTypeOrNull(), file!!)
        //val body = MultipartBody.Part.createFormData("audio", file!!.name, requestBody)
        //       Log.d("cvxcv",file!!.name.toString())

        builder.addFormDataPart("voice_notes", file.name ,
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file))

        Log.d("requestParms", Gson().toJson(builder))
        apiClient.progressView.showLoader()
        apiClient.makeCallMultipart(ApiContants.addVoiceNote, builder.build())

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()

            if (tag == ApiContants.getUpdateLead) {
                val baseResponseBean = apiClient.getConvertIntoModel<ProfileBean>(
                    jsonElement.toString(),
                    ProfileBean::class.java
                )
                Toast.makeText(this, baseResponseBean.msg, Toast.LENGTH_SHORT).show()

                finish()
            }
        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }
    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(this, errorMessage)
        Log.d("cvxcv",errorMessage)
    }
}