package com.example.rentalexpertz.Activity

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.rentalexpertz.ApiHelper.ApiController
import com.example.rentalexpertz.ApiHelper.ApiResponseListner
import com.example.rentalexpertz.Model.*
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
import java.io.IOException

class RecordActivity : AppCompatActivity() , View.OnClickListener, ApiResponseListner {
    private var file: File?=null
    private var mRecorder: MediaRecorder? = null
    private var mPlayer: MediaPlayer? = null
    private var fileName: String? = null
    private var lastProgress = 0
    private val mHandler = Handler()
    private val RECORD_AUDIO_REQUEST_CODE = 101
    private var isPlaying = false
    var leadID=""
    private lateinit var apiClient: ApiController
    private lateinit var binding: com.example.rentalexpertz.databinding.ActivityRecordBinding

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.imgBtRecord -> {
                prepareRecording()
                startRecording()
            }

            R.id.imgBtStop -> {
                prepareStop()
                stopRecording()
            }

            R.id.imgViewPlay -> {
                if (!isPlaying && fileName != null) {
                    isPlaying = true
                    startPlaying()
                } else {
                    isPlaying = false
                    stopPlaying()
                }
            }

         /*   R.id.imgBtRecordList -> {
               startActivity(Intent(this, RecordListActivity::class.java))
            }*/
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record)
     //   setContentView(R.layout.activity_record)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermissionToRecordAudio()
        }
        apiClient = ApiController(this, this@RecordActivity)
         leadID= intent.getStringExtra("leadID")!!
        binding.imgBtRecord.setOnClickListener(this)
        binding.imgBtStop.setOnClickListener(this)
        binding.imgViewPlay.setOnClickListener(this)
        binding.btnUploadToServer.setOnClickListener {
            apiUploadRecording(leadID)
        }
       // binding.imgBtRecordList.setOnClickListener(this)

    }

    private fun prepareStop() {
        TransitionManager.beginDelayedTransition(binding.llRecorder)
        binding.imgBtRecord.visibility = View.VISIBLE
        binding.imgBtStop.visibility = View.GONE
        binding.llPlay.visibility = View.VISIBLE
    }

    private fun prepareRecording() {
        TransitionManager.beginDelayedTransition(binding.llRecorder)
        binding.imgBtRecord.visibility = View.GONE
        binding.imgBtStop.visibility = View.VISIBLE
        binding.llPlay.visibility = View.GONE
    }

    private fun stopPlaying() {
        try {
            mPlayer!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mPlayer = null
        //showing the play button
        binding.imgViewPlay.setImageResource(R.drawable.ic_play_circle)
        binding.chronometer.stop()
    }

    private fun startRecording() {
        mRecorder = MediaRecorder()
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        val root = android.os.Environment.getExternalStorageDirectory()
         file = File(root.absolutePath + "/RentalExpertz/Audios")

        if (!file!!.exists()) {
            file!!.mkdirs()
          //  file!!.parentFile.mkdirs()

        }

        fileName = root.absolutePath + "/RentalExpertz/Audios/" + (System.currentTimeMillis().toString() + ".mp3")
        Log.d("filename", fileName!!)
        mRecorder!!.setOutputFile(fileName)
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mRecorder!!.prepare()
            mRecorder!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        lastProgress = 0
        binding.seekBar.progress = 0
        stopPlaying()
        // making the imageView a stop button starting the chronometer
        binding.chronometer.base = SystemClock.elapsedRealtime()
        binding.chronometer.start()
    }

    private fun stopRecording() {
        try {
            mRecorder!!.stop()
            mRecorder!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mRecorder = null
        //starting the chronometer
        binding.chronometer.stop()
        binding.chronometer.base = SystemClock.elapsedRealtime()
        //showing the play button
        Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show()
    }

    private fun startPlaying() {
        mPlayer = MediaPlayer()
        try {
            mPlayer!!.setDataSource(fileName)
            mPlayer!!.prepare()
            mPlayer!!.start()
       //     apiUploadRecording(leadID)
        } catch (e: IOException) {
            Log.e("LOG_TAG", "prepare() failed")
        }

        //making the imageView pause button
        binding.imgViewPlay.setImageResource(R.drawable.ic_pause_circle)

        binding.seekBar.progress = lastProgress
        mPlayer!!.seekTo(lastProgress)
        binding.seekBar.max = mPlayer!!.duration
        seekBarUpdate()
        binding.chronometer.start()

        mPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            binding.imgViewPlay.setImageResource(R.drawable.ic_play_circle)
            isPlaying = false
            binding.chronometer.stop()
            binding.chronometer.base = SystemClock.elapsedRealtime()
            mPlayer!!.seekTo(0)
        })

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mPlayer != null && fromUser) {
                    mPlayer!!.seekTo(progress)
                    binding.chronometer.base = SystemClock.elapsedRealtime() - mPlayer!!.currentPosition
                    lastProgress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private var runnable: Runnable = Runnable { seekBarUpdate() }

    private fun seekBarUpdate() {
        if (mPlayer != null) {
            val mCurrentPosition = mPlayer!!.currentPosition
            binding.seekBar.progress = mCurrentPosition
            lastProgress = mCurrentPosition
        }
        mHandler.postDelayed(runnable, 100)
    }

    private fun getPermissionToRecordAudio() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid checking the build version since Context.checkSelfPermission(...) is only available in Marshmallow
        // 2) Always check for permission (even if permission has already been granted) since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), RECORD_AUDIO_REQUEST_CODE)
        }
    }

    // Callback with the request from calling requestPermissions(...)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
    }


    fun apiUploadRecording(leadID: String?) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("lead_id",leadID!!)
      //  builder.addFormDataPart("voice_notes","4433123")
        //val requestBody = RequestBody.create("audio/*".toMediaTypeOrNull(), file!!)
        //val body = MultipartBody.Part.createFormData("audio", file!!.name, requestBody)
 //       Log.d("cvxcv",file!!.name.toString())

        builder.addFormDataPart("voice_notes", "8787878787" ,
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "4545454545"))

        Log.d("requestParms", Gson().toJson(builder))
        apiClient.progressView.showLoader()
        apiClient.makeCallMultipart(ApiContants.addVoiceNote, builder.build())

    }

    fun getRecord() {
        val root = android.os.Environment.getExternalStorageDirectory()
        val path = root.absolutePath + "/RentalExpertz/Audios"
        val directory = File(path)
        val files = directory.listFiles()
        if (files != null) {

            for (i in files.indices) {
                val fileName = files[i].name
                val recordingUri = root.absolutePath + "/RentalExpertz/Audios/" + fileName
             //   recordArrayList.add(Recording(recordingUri, fileName, false))
            }
        }
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