package com.example.rentalexpertz.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rentalexpertz.Activity.DashboardActivity
import com.example.rentalexpertz.Activity.LoginActivity
import com.example.rentalexpertz.Activity.PasswordChnageActivity
import com.example.rentalexpertz.Activity.ProfileActivity
import com.example.rentalexpertz.ApiHelper.ApiController
import com.example.rentalexpertz.ApiHelper.ApiResponseListner
import com.example.rentalexpertz.Model.BaseResponseBean
import com.example.rentalexpertz.Model.PasswordChangeBean
import com.example.rentalexpertz.Model.ProfileBean
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.GeneralUtilities
import com.example.rentalexpertz.Utills.PrefManager
import com.example.rentalexpertz.Utills.SalesApp
import com.example.rentalexpertz.Utills.Utility

import com.example.rentalexpertz.databinding.FragmentSettingBinding
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants


class SettingFragment : Fragment(), ApiResponseListner {
    private lateinit var apiClient: ApiController
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val titleText = (activity as DashboardActivity?)
        titleText?.setTitle("Setting")
        apiProfileData()
        binding.apply {

            llChangePass.setOnClickListener {
           //  startActivity(Intent(requireContext(),PasswordChnageActivity::class.java))
                openPassChangeDialog()
            }

            llLogout.setOnClickListener {
                dialogLogout()
            }

        }

        return root
    }

    fun apiProfileData() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        //     params["password"] = binding.editNewpass.text.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getProfile, params)

    }

    fun dialogLogout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to logout app?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database

                apiCallLogout()
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }

    fun apiCallLogout() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(requireContext(), this)
        val params = Utility.getParmMap()
        params["mobile"] = PrefManager.getString(ApiContants.mobileNumber, "")
        params["password"] = PrefManager.getString(ApiContants.password, "")
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.logout, params)
    }

    fun apiChangePassword(editNewpass: String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["password"] = editNewpass
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getPasswordChange, params)

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.logout) {
                val baseResponseBean = apiClient.getConvertIntoModel<BaseResponseBean>(
                    jsonElement.toString(),
                    BaseResponseBean::class.java
                )
                Toast.makeText(activity, baseResponseBean.msg, Toast.LENGTH_SHORT).show()
                PrefManager.clear()
                GeneralUtilities.launchActivity(
                    requireContext() as AppCompatActivity?,
                    LoginActivity::class.java
                )
                requireActivity().finishAffinity()
            }
            if (tag == ApiContants.getPasswordChange) {
                val updateLeadBean = apiClient.getConvertIntoModel<PasswordChangeBean>(
                    jsonElement.toString(),
                    PasswordChangeBean::class.java
                )

                Toast.makeText(requireContext(), updateLeadBean.msg, Toast.LENGTH_SHORT).show()

            }
            if (tag == ApiContants.getProfile) {
                val profileBean = apiClient.getConvertIntoModel<ProfileBean>(
                    jsonElement.toString(),
                    ProfileBean::class.java
                )
                setData(profileBean.data)
                Toast.makeText(requireContext(), profileBean.msg, Toast.LENGTH_SHORT).show()

            }


        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }
    }

    private fun setData(data: ProfileBean.Data) {
    binding.apply {

    tvName.setText(data.name)
    tvMobNo.setText(data.mobile)
    tvEmail.setText(data.email)

    llProfile.setOnClickListener {
        startActivity(Intent(requireContext(),ProfileActivity::class.java).putExtra("profileResponse",data))
    }
    rlProfile.setOnClickListener {
        startActivity(Intent(requireContext(),ProfileActivity::class.java).putExtra("profileResponse",data))
    }
  }
    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(requireActivity(), errorMessage)
    }

    fun openPassChangeDialog() {
        val dialog: Dialog = GeneralUtilities.openBootmSheetDailog(
            R.layout.dialog_pass_change, R.style.AppBottomSheetDialogTheme,
            requireActivity()
        )
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val edPassChange = dialog.findViewById<View>(R.id.edPassChange) as EditText
        val btnSubmit = dialog.findViewById<View>(R.id.btnSubmit) as TextView
        btnSubmit.setOnClickListener {
            apiChangePassword(edPassChange.text.toString()
            )
            dialog.dismiss()
        }


        ivClose.setOnClickListener { dialog.dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}