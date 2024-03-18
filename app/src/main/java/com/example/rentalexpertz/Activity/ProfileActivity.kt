package com.example.rentalexpertz.Activity

import android.app.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.rentalexpertz.ApiHelper.ApiController
import com.example.rentalexpertz.ApiHelper.ApiResponseListner
import com.example.rentalexpertz.Model.ProfileBean
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.ConnectivityListener
import com.example.rentalexpertz.Utills.GeneralUtilities
import com.example.rentalexpertz.Utills.SalesApp
import com.example.rentalexpertz.Utills.Utility
import com.example.rentalexpertz.databinding.ActivityProfileBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants


class ProfileActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var apiClient: ApiController

    var myReceiver: ConnectivityListener? = null
    var activity: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        if (SalesApp.isEnableScreenshort == true) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()
        binding.igToolbar.tvTitle.text = "My Profile"
        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivMenu.visibility = View.VISIBLE
        val profileData = intent.getSerializableExtra("profileResponse") as ProfileBean.Data
        Log.d("zxvcxc", Gson().toJson(profileData))
        setData(profileData)
        binding.apply {
            btnSubmit.setOnClickListener {

            }
        }
    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            /*        if (tag == ApiContants.getProfile) {
                        val profileBean = apiClient.getConvertIntoModel<ProfileBean>(
                            jsonElement.toString(),
                            ProfileBean::class.java
                        )
                        setData(profileBean.data)
                        Toast.makeText(this, profileBean.msg, Toast.LENGTH_SHORT).show()
                        finish()
                    }

        */
        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }
    }

    private fun setData(data: ProfileBean.Data) {
        binding.apply {
            edFullName.setText(data.name.toString())
            edEmail.setText(data.email.toString())
            edMobNo.setText(data.mobile.toString())
            //    edAddress.setText(data.address.toString())
        }
    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()

        Utility.showSnackBar(activity, errorMessage)
        Log.d("error", errorMessage)

    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
        GeneralUtilities.unregisterBroadCastReceiver(this, myReceiver)
    }

    override fun onResume() {
        GeneralUtilities.registerBroadCastReceiver(this, myReceiver)
        SalesApp.setConnectivityListener(this)
        super.onResume()
    }

    override fun onNetworkConnectionChange(isconnected: Boolean) {
        ApiContants.isconnectedtonetwork = isconnected
        GeneralUtilities.internetConnectivityAction(this, isconnected)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}
    override fun onDestroy() {
        super.onDestroy()
        // Start the LocationService when the app is closed
        //   startService(Intent(this, LocationService::class.java))
    }
}
