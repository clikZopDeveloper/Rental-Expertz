package com.example.rentalexpertz.Activity

import android.content.pm.PackageManager
import android.media.AudioFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.rentalexpertz.R
import com.github.squti.androidwaverecorder.RecorderState
import com.github.squti.androidwaverecorder.WaveRecorder
import java.util.*
import java.util.concurrent.TimeUnit

class RecordingActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_RECORD_AUDIO = 77
    private lateinit var waveRecorder: WaveRecorder
    private lateinit var filePath: String
    private var isRecording = false
    private var isPaused = false
    private lateinit var binding: com.example.rentalexpertz.databinding.ActivityRecordingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recording)

        filePath = filesDir.absolutePath + "/audioFile.wav"

        waveRecorder = WaveRecorder(filePath)
        waveRecorder.waveConfig.sampleRate = 44100
        waveRecorder.waveConfig.channels = AudioFormat.CHANNEL_IN_STEREO
        waveRecorder.waveConfig.audioEncoding = AudioFormat.ENCODING_PCM_FLOAT

        waveRecorder.onStateChangeListener = {
            when (it) {
                RecorderState.RECORDING -> startRecording()
                RecorderState.STOP -> stopRecording()
                RecorderState.PAUSE -> pauseRecording()
            }
        }

        waveRecorder.onTimeElapsed = {
            Log.e(TAG, "onCreate: time elapsed $it")
            binding.timeTextView.text = formatTimeUnit(it * 1000)
        }

        binding.startStopRecordingButton.setOnClickListener {

            if (!isRecording) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.RECORD_AUDIO
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.RECORD_AUDIO),
                        PERMISSIONS_REQUEST_RECORD_AUDIO
                    )
                } else {
                    waveRecorder.startRecording()
                }
            } else {
                waveRecorder.stopRecording()
            }
        }

        binding.pauseResumeRecordingButton.setOnClickListener {
            if (!isPaused) {
                waveRecorder.pauseRecording()
            } else {
                waveRecorder.resumeRecording()
            }
        }

        binding.showAmplitudeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.amplitudeTextView.text = "Amplitude : 0"
                binding.amplitudeTextView.visibility = View.VISIBLE
                waveRecorder.onAmplitudeListener = {
                    binding.amplitudeTextView.text = "Amplitude : $it"
                }

            } else {
                waveRecorder.onAmplitudeListener = null
                binding.amplitudeTextView.visibility = View.GONE
            }
        }

        binding.noiseSuppressorSwitch.setOnCheckedChangeListener { _, isChecked ->
            waveRecorder.noiseSuppressorActive = isChecked
            if (isChecked)
                Toast.makeText(this, "Noise Suppressor Activated", Toast.LENGTH_SHORT).show()

        }

    }

    private fun startRecording() {
        Log.d(TAG, waveRecorder.audioSessionId.toString())
        isRecording = true
        isPaused = false
        binding.messageTextView.visibility = View.GONE
        binding.recordingTextView.text = "Recording..."
        binding.recordingTextView.visibility = View.VISIBLE
        binding.startStopRecordingButton.text = "STOP"
        binding.pauseResumeRecordingButton.text = "PAUSE"
        binding.pauseResumeRecordingButton.visibility = View.VISIBLE
        binding.noiseSuppressorSwitch.isEnabled = false
    }

    private fun stopRecording() {
        isRecording = false
        isPaused = false
        binding.recordingTextView.visibility = View.GONE
        binding.messageTextView.visibility = View.VISIBLE
        binding.pauseResumeRecordingButton.visibility = View.GONE
        binding.showAmplitudeSwitch.isChecked = false
        Log.d("sfsdf",filePath)
        Toast.makeText(this, "File saved at : $filePath", Toast.LENGTH_LONG).show()
        binding.startStopRecordingButton.text = "START"
        binding.noiseSuppressorSwitch.isEnabled = true
    }

    private fun pauseRecording() {
        binding.recordingTextView.text = "PAUSE"
        binding.pauseResumeRecordingButton.text = "RESUME"
        isPaused = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_RECORD_AUDIO -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    waveRecorder.startRecording()
                }
                return
            }

            else -> {
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    private fun formatTimeUnit(timeInMilliseconds: Long): String {
        return try {
            String.format(
                Locale.getDefault(),
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds),
                TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds)
                )
            )
        } catch (e: Exception) {
            "00:00"
        }
    }

}