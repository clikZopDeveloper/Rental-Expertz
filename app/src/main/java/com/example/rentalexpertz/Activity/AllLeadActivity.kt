package com.example.rentalexpertz.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rentalexpertz.Adapter.*
import com.example.rentalexpertz.ApiHelper.ApiController
import com.example.rentalexpertz.ApiHelper.ApiResponseListner
import com.example.rentalexpertz.Model.*
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.*
import com.example.rentalexpertz.databinding.ActivityAllLeadBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import java.io.File
import java.io.IOException
import java.util.ArrayList

class AllLeadActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {

    private var statusLisst: List<String>?=null
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private var permissionToRecordAccepted = false
    private val permissions: Array<String> = arrayOf(android.Manifest.permission.RECORD_AUDIO)

    private var mediaRecorder: MediaRecorder? = null
    private var output: String = ""


    private lateinit var binding: ActivityAllLeadBinding
    private lateinit var apiClient: ApiController
    var myReceiver: ConnectivityListener? = null
    private lateinit var mAllAdapter: AllLeadAdapter
    var activity: Activity = this
    var leadID = 0
    var conversion = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_lead)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        );

        myReceiver = ConnectivityListener()

        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivLogout.visibility = View.GONE
        binding.igToolbar.ivMenu.visibility = View.VISIBLE
        binding.igToolbar.switchDayStart.visibility = View.GONE
        apiClient = ApiController(this, this)

        if (intent.getStringExtra("leadStatus").equals("")){
            binding.igToolbar.tvTitle.text = "All Lead"
        }else{
            binding.igToolbar.tvTitle.text = intent.getStringExtra("leadStatus")
        }

        if (intent.hasExtra("conversion")) {
            conversion = intent.getStringExtra("conversion")!!
            if (conversion.equals("Partial")) {//Partial Converted
                binding.igToolbar.tvTitle.text = "Partial Converted"
            } else if (conversion.equals("Completed")) {//Complete Lead
                binding.igToolbar.tvTitle.text = "Complete Lead"
            } else {
            }
        }

    //    requestAudioPermissions()
        apiAllGet()
        binding.startRecordingButton.setOnClickListener {
            setupMediaRecorder()
            startRecording()
            binding.startRecordingButton.isEnabled = false
            binding.stopRecordingButton.isEnabled = true
        }

        binding.stopRecordingButton.setOnClickListener {
            stopRecording()
            binding.startRecordingButton.isEnabled = true
            binding.stopRecordingButton.isEnabled = false
        }
        binding.selectStatus.setOnClickListener {
            setStatusData()
        }

        binding.swipeReferesh.setOnRefreshListener {
            intent.getStringExtra("leadStatus")?.let { apiAllLead(it) }
            binding.swipeReferesh.isRefreshing = false

        }
    }
    fun apiAllGet() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getStatus, params)
    }

    fun apiAllLead(status: String) {
        SalesApp.isAddAccessToken = true

        val params = Utility.getParmMap()
        params["status"] = status
        params["conversion"] = conversion
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.AllLeadData, params)
    }

    fun handleAllLead(data: List<AllLeadDataBean.Data>) {
        binding.rcAllLead.layoutManager = LinearLayoutManager(this)
        mAllAdapter = AllLeadAdapter(this, data, intent.getStringExtra("leadStatus"), object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {
                leadID = pos
                apiLeadDetail(pos)
            }
        })
        binding.rcAllLead.adapter = mAllAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false
        binding.rcAllLead.isNestedScrollingEnabled = false
        mAllAdapter.notifyDataSetChanged()

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (data != null) {
                    mAllAdapter.filter.filter(s)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                mAllAdapter.filter.filter(s)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAllAdapter.filter.filter(s)
                /* if (s.toString().trim { it <= ' ' }.length < 1) {
                     ivClear.visibility = View.GONE
                 } else {
                     ivClear.visibility = View.GONE
                 }*/
            }
        })

    }

    @SuppressLint("SuspiciousIndentation")
    override fun success(tag: String?, jsonElement: JsonElement?) {
        try {
            apiClient.progressView.hideLoader()

            if (tag == ApiContants.AllLeadData) {
                val allLeadDataBean = apiClient.getConvertIntoModel<AllLeadDataBean>(
                    jsonElement.toString(),
                    AllLeadDataBean::class.java
                )
                //   Toast.makeText(this, allStatusBean.msg, Toast.LENGTH_SHORT).show()
                if (allLeadDataBean.error == false) {
                    handleAllLead(allLeadDataBean.data)
                }
            }

            if (tag == ApiContants.LeadDetail) {
                val leadDeatilBean = apiClient.getConvertIntoModel<LeadDetailBean>(
                    jsonElement.toString(),
                    LeadDetailBean::class.java
                )
                //   Toast.makeText(this, allStatusBean.msg, Toast.LENGTH_SHORT).show()
                if (leadDeatilBean.error == false) {
                    setLeadDetailDialog(leadDeatilBean.data)
                }
            }
            if (tag == ApiContants.getStatus) {
                val statusBean = apiClient.getConvertIntoModel<GetAllStatusBean>(
                    jsonElement.toString(),
                    GetAllStatusBean::class.java
                )
                if (statusBean.error == false) {
                   statusLisst=statusBean.data
                }
            }
        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }
    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(this, errorMessage)
    }

    fun apiLeadDetail(leadID: Int) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val params = Utility.getParmMap()
        params["lead_id"] = leadID.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.LeadDetail, params)
    }

    fun setLeadDetailDialog(data: LeadDetailBean.Data) {
        val builder = AlertDialog.Builder(this, 0).create()
        val dialog = layoutInflater.inflate(R.layout.dailog_leaddetail, null)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val ivCall = dialog.findViewById<ImageView>(R.id.ivCall)
        val tvStatus = dialog.findViewById<TextView>(R.id.tvStatus)
        val tvType = dialog.findViewById<TextView>(R.id.tvType)
        val tvCategory = dialog.findViewById<TextView>(R.id.tvCategory)
        val tvSubCategory = dialog.findViewById<TextView>(R.id.tvSubCategory)
        val tvSource = dialog.findViewById<TextView>(R.id.tvSource)
        val tvState = dialog.findViewById<TextView>(R.id.tvState)
        val tvCity = dialog.findViewById<TextView>(R.id.tvCity)
        val tvAddress = dialog.findViewById<TextView>(R.id.tvAddress)
        val tvEmail = dialog.findViewById<TextView>(R.id.tvEmail)
        val tvName = dialog.findViewById<TextView>(R.id.tvName)
        val tvClientNumber = dialog.findViewById<TextView>(R.id.tvMobNumber)
        val tvClassification = dialog.findViewById<TextView>(R.id.tvClassification)
        val tvProject = dialog.findViewById<TextView>(R.id.tvProject)
        val tvCampaign = dialog.findViewById<TextView>(R.id.tvCampaign)
        val tvDOA = dialog.findViewById<TextView>(R.id.tvDOA)
        val tvDoB = dialog.findViewById<TextView>(R.id.tvDoB)
        val rcCommentList = dialog.findViewById<RecyclerView>(R.id.rcCommentList)

        builder.setCanceledOnTouchOutside(false)
        builder.setView(dialog)
        builder.show()
        ivClose.setOnClickListener {
            builder.dismiss()
        }
        ivCall.setOnClickListener {
           // builder.dismiss()
            GeneralUtilities.getInstance().makeCall(this,data.leadData.get(0).mobile.toString())
        }

        if (!data.leadData.get(0).name.toString().isNullOrEmpty()) tvName.setText(data.leadData.get(0).name.toString())
        if (!data.leadData.get(0).email.toString().isNullOrEmpty()) tvEmail.setText(data.leadData.get(0).email.toString())
        if (!data.leadData.get(0).mobile.toString().isNullOrEmpty()) tvClientNumber.setText(data.leadData.get(0).mobile.toString())
        if (!data.leadData.get(0).address.toString().isNullOrEmpty()) tvAddress.setText(data.leadData.get(0).address.toString())
        if (!data.leadData.get(0).status.toString().isNullOrEmpty()) tvStatus.setText(data.leadData.get(0).status.toString())
        if (!data.leadData.get(0).city.toString().isNullOrEmpty()) tvCity.setText(data.leadData.get(0).city.toString())
        if (!data.leadData.get(0).campaign.toString().isNullOrEmpty()) tvCampaign.setText(data.leadData.get(0).campaign.toString())

        if (!data.leadData.get(0).state.toString().isNullOrEmpty()) tvState.setText(data.leadData.get(0).state.toString())
        if (!data.leadData.get(0).classification.toString().isNullOrEmpty()) tvClassification.setText(data.leadData.get(0).classification.toString())
        if (!data.leadData.get(0).project.toString().isNullOrEmpty()) tvProject.setText(data.leadData.get(0).project.toString())
        if (!data.leadData.get(0).category.toString().isNullOrEmpty()) tvCategory.setText(data.leadData.get(0).category.toString())
        if (!data.leadData.get(0).subCategory.toString().isNullOrEmpty()) tvSubCategory.setText(data.leadData.get(0).subCategory.toString())
        if (!data.leadData.get(0).source.toString().isNullOrEmpty()) tvSource.setText(data.leadData.get(0).source.toString())
        if (!data.leadData.get(0).type.toString().isNullOrEmpty()) tvType.setText(data.leadData.get(0).type.toString())
        if (!data.leadComments.isNullOrEmpty()) handleLeadCommentList(rcCommentList, data.leadComments)
        if (!data.leadData.get(0).dob.toString().isNullOrEmpty()) tvDoB.setText("DOB : "+data.leadData.get(0).dob.toString())
        if (!data.leadData.get(0).doa.toString().isNullOrEmpty()) tvDOA.setText("DOA : "+data.leadData.get(0).doa.toString())


    }
    fun handleLeadCommentList(
        rcCommentList: RecyclerView,
        leadProduct: List<LeadDetailBean.Data.LeadComment>
    ) {
        rcCommentList.layoutManager = LinearLayoutManager(this)
        var mAdapter = LeadCommentListAdapter(this, leadProduct, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        rcCommentList.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }
    private fun openFullScreenDialog(imgUrl: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.fullscreen_dailog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

        val ivFullImage = dialog.findViewById<ImageView>(R.id.ivFullImage)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val ivDownload = dialog.findViewById<ImageView>(R.id.ivDownload)
        //     ivClose.background = RoundView(Color.BLACK, RoundView.getRadius(100f))
        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        ivDownload.setOnClickListener {
            GeneralUtilities.downloadUrl(activity, ApiContants.downloadUrl + imgUrl)
        }

        Glide.with(this)
            .load(ApiContants.BaseUrl + imgUrl)
            .into(ivFullImage)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 101) {
            val leadStatus: String = data?.getStringExtra("leadStatus")!!
            Log.d("zxczc", leadStatus)
            apiAllLead(leadStatus)
        }

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
        intent.getStringExtra("leadStatus")?.let { apiAllLead(it) }
    }

    override fun onNetworkConnectionChange(isconnected: Boolean) {
        ApiContants.isconnectedtonetwork = isconnected
        GeneralUtilities.internetConnectivityAction(this, isconnected)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    override fun onDestroy() {
        super.onDestroy()
        // Start the LocationService when the app is closed
        //  startService(Intent(this, LocationService::class.java))
    }

    fun setStatusData() {
        val state = arrayOfNulls<String>(statusLisst!!.size)
        for (i in statusLisst!!.indices) {
            //Storing names to string array
            state[i] =statusLisst!!.get(i)
        }
        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AllLeadActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.selectStatus.setAdapter(adapte1)
        binding.selectStatus.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            binding.selectStatus.setText(parent.getItemAtPosition(position).toString())

            Log.d("StateID", "" + parent.getItemAtPosition(position).toString())
           /* for (catData in getMenus()) {
                if (catData.name.equals(
                        parent.getItemAtPosition(position).toString()
                    )
                ) {
                    binding.selectStatus.setText(parent.getItemAtPosition(position).toString())
              //      SubCatID = catData.id
                    Log.d("StateID", "" + catData.id)
                }
           }*/
            apiAllLead(binding.selectStatus.getText().toString())
            Toast.makeText(
                applicationContext,
                binding.selectStatus.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            setStatusData()

        })
    }

    private fun getMenus(): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        menuList.add(MenuModelBean(0, "new lead", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(1, "pending", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "processing", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(3, "converted", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(4, "call scheduled", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(5, "visit scheduled", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(6, "visit done", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(7, "booked", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(8, "completed", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(9, "cancelled", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(10, "not interested", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(11, "not picked", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(12, "wrong number", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(13, "not reachable", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(14, "channel partner", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(15, "future lead", "", R.drawable.ic_dashbord))

        return menuList
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    private fun requestAudioPermissions() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    private fun setupMediaRecorder() {
        output = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(output)
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
}
