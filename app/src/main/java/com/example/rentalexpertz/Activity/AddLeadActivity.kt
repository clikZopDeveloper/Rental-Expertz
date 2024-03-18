package com.example.rentalexpertz.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.rentalexpertz.ApiHelper.ApiController
import com.example.rentalexpertz.ApiHelper.ApiResponseListner
import com.example.rentalexpertz.Model.*
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.*
import com.example.rentalexpertz.databinding.ActivityAddLeadBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants


class AddLeadActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityAddLeadBinding
    private lateinit var apiClient: ApiController
    var myReceiver: ConnectivityListener? = null
    var activity: Activity = this

    var CatID = 0
    var SubCatID = 0
    var projectID = 0
    var classificationID = 0
    var sourceID = 0
    var typeID = 0
    var campignID = 0
    var leadID = 0

    var way = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_lead)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        );
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()
        binding.apply {

       //     igToolbar.tvTitle.text = "Add Lead"
            igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
            igToolbar.ivMenu.setOnClickListener { finish() }
            igToolbar.ivMenu.visibility = View.VISIBLE
            igToolbar.ivLogout.visibility = View.GONE
            igToolbar.switchDayStart.visibility = View.GONE

            apiClient = ApiController(activity, this@AddLeadActivity)
            way = intent.getStringExtra("way").toString()
            Log.d("zzxc",way)
            if (way.equals("Add Lead")) {
                binding.igToolbar.tvTitle.text = way
            } else {
                try {

                    val wayResponse =
                        intent.getSerializableExtra("wayResponse") as AllLeadDataBean.Data
                    igToolbar.tvTitle.text = "Lead Update"
                    leadID = wayResponse.id
                    edFullName.setText(wayResponse.name.toString())
                    edEmail.setText(wayResponse.email.toString())
                    edContactNumber.setText(wayResponse.mobile.toString())
                    stateselector.setText(wayResponse.state.toString())
                    cityselector.setText(wayResponse.city.toString())
                    editAddress.setText(wayResponse.address.toString())
                    cityCampigns.setText(wayResponse.campaign.toString())
                    editComments.setText(wayResponse.comments.toString())
                    stateCategory.setText(wayResponse.category.toString())
                    stateSubCategory.setText(wayResponse.subCategory.toString())
                    stateType.setText(wayResponse.type.toString())
                    stateSource.setText(wayResponse.source.toString())
                    stateClassification.setText(wayResponse.classification.toString())
                    stateProject.setText(wayResponse.project.toString())


                     CatID = wayResponse.categoryId
                     SubCatID = wayResponse.subCategoryId
                     projectID = wayResponse.projectId
                     classificationID = wayResponse.classificationId
                     sourceID = wayResponse.sourceId
                     typeID = wayResponse.typeId
                     campignID = wayResponse.campaignId.toInt()

                    Log.d("error>>>>>>>>>", Gson().toJson(wayResponse))
                } catch (e: Exception) {
                    Log.d("error>>>>>>>>>", e.localizedMessage)
                }
            }
            apiAllGet()
            setState()

            binding.btnSubmit.setOnClickListener {
                apiAddLead()
            }
        }
    }

    fun apiAddLead() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        params["name"] = binding.edFullName.text.toString()
        params["email"] = binding.edEmail.text.toString()
        params["mobile"] = binding.edContactNumber.text.toString()
        params["state"] = binding.stateselector.text.toString()
        params["city"] = binding.cityselector.text.toString()
        params["address"] = binding.editAddress.text.toString()
        params["campaign"] = campignID.toString()
        params["comment"] = binding.editComments.text.toString()
        params["type"] = typeID.toString()
        params["category_id"] = CatID.toString()
        params["sub_category_id"] = SubCatID.toString()
        params["source"] = sourceID.toString()
        params["classification"] = classificationID.toString()
        params["project"] = projectID.toString()

        apiClient.progressView.showLoader()
        if (way.equals("Add Lead")) {
            apiClient.getApiPostCall(ApiContants.getAddLead, params)
        } else {
            params["id"] = leadID.toString()
            apiClient.getApiPostCall(ApiContants.getUpdateLead, params)
        }


    }

    fun apiAllGet() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.getApiPostCall(ApiContants.getSource, params)
        apiClient.getApiPostCall(ApiContants.getCampigns, params)
        apiClient.getApiPostCall(ApiContants.getType, params)
        apiClient.getApiPostCall(ApiContants.getCategory, params)
        apiClient.getApiPostCall(ApiContants.getClassification, params)
        apiClient.getApiPostCall(ApiContants.getProject, params)
    }

    fun apiCity(stateName: String) {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        params["state"] = stateName
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getCity, params)
    }

    fun apiSubCatory(subCatId: Int) {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        params["catg_id"] = subCatId.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getSubCategory, params)
    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()

            if (tag == ApiContants.getAddLead) {
                val baseResponseBean = apiClient.getConvertIntoModel<ProfileBean>(
                    jsonElement.toString(),
                    ProfileBean::class.java
                )
                Toast.makeText(this, baseResponseBean.msg, Toast.LENGTH_SHORT).show()

                finish()
            }
            if (tag == ApiContants.getUpdateLead) {
                val baseResponseBean = apiClient.getConvertIntoModel<ProfileBean>(
                    jsonElement.toString(),
                    ProfileBean::class.java
                )
                Toast.makeText(this, baseResponseBean.msg, Toast.LENGTH_SHORT).show()

                finish()
            }

            if (tag == ApiContants.getCity) {
                val cityBean = apiClient.getConvertIntoModel<CityBean>(
                    jsonElement.toString(),
                    CityBean::class.java
                )
                if (cityBean.error == false) {
                    val state = arrayOfNulls<String>(cityBean.data.size)
                    for (i in cityBean.data.indices) {
                        //Storing names to string array
                        state[i] = cityBean.data.get(i).district
                    }
                    val adapte1: ArrayAdapter<String?>
                    adapte1 = ArrayAdapter(
                        this@AddLeadActivity,
                        android.R.layout.simple_list_item_1,
                        state
                    )
                    binding.cityselector.setAdapter(adapte1)
                    binding.cityselector.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->

                        binding.cityselector.setText(parent.getItemAtPosition(position).toString())
                        Log.d("StateID", "" + parent.getItemAtPosition(position).toString())
                        Toast.makeText(
                            applicationContext,
                            binding.cityselector.getText().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        apiCity(binding.stateselector.text.toString())
                    })
                }
            }

            if (tag == ApiContants.getSource) {
                val sourceBean = apiClient.getConvertIntoModel<SourceBean>(
                    jsonElement.toString(),
                    SourceBean::class.java
                )
                if (sourceBean.error == false) {
                    setSourceData(sourceBean.data)
                }
            }
            if (tag == ApiContants.getCampigns) {
                val campignsBean = apiClient.getConvertIntoModel<CampignsBean>(
                    jsonElement.toString(),
                    CampignsBean::class.java
                )
                if (campignsBean.error == false) {
                    setCampignsData(campignsBean.data)
                }
            }

            if (tag == ApiContants.getType) {
                val typeBean = apiClient.getConvertIntoModel<TypeBean>(
                    jsonElement.toString(),
                    TypeBean::class.java
                )
                if (typeBean.error == false) {
                    setTypeData(typeBean.data)
                }
            }
            if (tag == ApiContants.getClassification) {
                val classificationBean = apiClient.getConvertIntoModel<ClassificationBean>(
                    jsonElement.toString(),
                    ClassificationBean::class.java
                )
                if (classificationBean.error == false) {
                    setClassificationData(classificationBean.data)
                    /* SalesApp.sourceList.clear()
                     SalesApp.sourceList.addAll(sourceBean.data)*/
                }
            }
            if (tag == ApiContants.getProject) {
                val projectTypeBean = apiClient.getConvertIntoModel<ProjectTypeBean>(
                    jsonElement.toString(),
                    ProjectTypeBean::class.java
                )
                if (projectTypeBean.error == false) {
                    setProjectTypeData(projectTypeBean.data)
                    /* SalesApp.sourceList.clear()
                     SalesApp.sourceList.addAll(sourceBean.data)*/
                }
            }
            if (tag == ApiContants.getCategory) {
                val categoryBean = apiClient.getConvertIntoModel<CategoryBean>(
                    jsonElement.toString(),
                    CategoryBean::class.java
                )
                if (categoryBean.error == false) {
                    setCategoryData(categoryBean.data)

                }
            }
            if (tag == ApiContants.getSubCategory) {
                val subCategoryBean = apiClient.getConvertIntoModel<SubCategoryBean>(
                    jsonElement.toString(),
                    SubCategoryBean::class.java
                )

                if (subCategoryBean.error == false) {
                    subCatData(subCategoryBean.data)
                }
            }


        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }
    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(activity, errorMessage)
    }

    fun subCatData(subCategoryBean: List<SubCategoryBean.Data>) {

        val state = arrayOfNulls<String>(subCategoryBean.size)
        for (i in subCategoryBean.indices) {
            //Storing names to string array
            state[i] = subCategoryBean.get(i).name
        }
        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddLeadActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.stateSubCategory.setAdapter(adapte1)
        binding.stateSubCategory.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->

            Log.d("StateID", "" + parent.getItemAtPosition(position).toString())
            for (catData in subCategoryBean) {
                if (catData.name.equals(
                        parent.getItemAtPosition(position).toString()
                    )
                ) {
                    binding.stateSubCategory.setText(parent.getItemAtPosition(position).toString())
                    SubCatID = catData.id
                    Log.d("StateID", "" + catData.id)
                }
            }

            Toast.makeText(
                applicationContext,
                binding.stateSubCategory.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            apiSubCatory(CatID)
        })
    }

    fun setSourceData(data: List<SourceBean.Data>) {
        //  binding.stateSource.setThreshold(1);//will start working from first character

        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        binding.stateSource.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateSource.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            //  var sourceName = data.get(position).name


            for (catData in data) {
                if (catData.name.equals(
                        parent.getItemAtPosition(position).toString()
                    )
                ) {
                    binding.stateSource.setText(parent.getItemAtPosition(position).toString())
                    sourceID = catData.id
                    Log.d("StateID", "" + catData.id)
                }
            }
            Toast.makeText(
                applicationContext,
                binding.stateSource.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            setSourceData(data)

        })
    }

    fun setCampignsData(data: List<CampignsBean.Data>) {
        //  binding.stateSource.setThreshold(1);//will start working from first character
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).campaignName
        }

        binding.cityCampigns.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.cityCampigns.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            //  var sourceName = data.get(position).name

            for (catData in data) {
                if (catData.campaignName.equals(
                        parent.getItemAtPosition(position).toString()
                    )
                ) {
                    binding.cityCampigns.setText(parent.getItemAtPosition(position).toString())
                    campignID = catData.id
                    Log.d("StateID", "" + catData.id)
                }
            }

            Toast.makeText(
                applicationContext,
                binding.cityCampigns.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            setCampignsData(data)

        })
    }

    fun setTypeData(data: List<TypeBean.Data>) {
        //  binding.stateSource.setThreshold(1);//will start working from first character

        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        binding.stateType.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateType.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            //  var sourceName = data.get(position).name

            for (catData in data) {
                if (catData.name.equals(
                        parent.getItemAtPosition(position).toString()
                    )
                ) {
                    binding.stateType.setText(parent.getItemAtPosition(position).toString())
                    typeID = catData.id
                    Log.d("StateID", "" + catData.id)
                }
            }
            Toast.makeText(
                applicationContext,
                binding.stateType.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            setTypeData(data)

        })
    }

    fun setClassificationData(data: List<ClassificationBean.Data>) {
        //  binding.stateSource.setThreshold(1);//will start working from first character

        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        binding.stateClassification.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateClassification.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            //  var sourceName = data.get(position).name

            for (catData in data) {
                if (catData.name.equals(
                        parent.getItemAtPosition(position).toString()
                    )
                ) {
                    binding.stateClassification.setText(
                        parent.getItemAtPosition(position).toString()
                    )
                    classificationID = catData.id
                    Log.d("StateID", "" + catData.id)
                }
            }
            Toast.makeText(
                applicationContext,
                binding.stateClassification.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            setClassificationData(data)

        })
    }

    fun setProjectTypeData(data: List<ProjectTypeBean.Data>) {
        //  binding.stateSource.setThreshold(1);//will start working from first character

        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        binding.stateProject.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateProject.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            //  var sourceName = data.get(position).name

            binding.stateProject.setText(parent.getItemAtPosition(position).toString())
            Log.d("StateID", "" + parent.getItemAtPosition(position).toString())

            for (catData in data) {
                if (catData.name.equals(
                        parent.getItemAtPosition(position).toString()
                    )
                ) {
                    binding.stateProject.setText(parent.getItemAtPosition(position).toString())
                    projectID = catData.id
                    Log.d("StateID", "" + catData.id)
                }
            }
            Toast.makeText(
                applicationContext,
                binding.stateProject.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            setProjectTypeData(data)

        })
    }

    fun setCategoryData(data: List<CategoryBean.Data>) {
        //  binding.stateSource.setThreshold(1);//will start working from first character
        /* for (i in data.indices) {
             //Storing names to string array
             val  id= data.get(i).id
             if (id.toString().equals(CatID)){
                 Log.d("czxc",data.get(i).name)
                 binding.stateCategory.setText(data.get(i).name)
             }
         }*/

        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        binding.stateCategory.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateCategory.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            //  var sourceName = data.get(position).name

            //   binding.stateCategory.setText(parent.getItemAtPosition(position).toString())
            Log.d("StateID", "" + parent.getItemAtPosition(position).toString())

            for (catData in data) {
                if (catData.name.equals(
                        parent.getItemAtPosition(position).toString()
                    )
                ) {
                    binding.stateCategory.setText(parent.getItemAtPosition(position).toString())
                    CatID = catData.id
                    Log.d("StateID", "" + catData.id)
                }
            }

            Toast.makeText(
                applicationContext,
                binding.stateCategory.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            setCategoryData(data)
            apiSubCatory(CatID)

        })
    }

    fun setState() {
        val state = arrayOfNulls<String>(SalesApp.stateList.size)
        for (i in SalesApp.stateList.indices) {
            state[i] = SalesApp.stateList.get(i).state
        }

        binding.stateselector.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateselector.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->

            binding.stateselector.setText(parent.getItemAtPosition(position).toString())
            Log.d("StateID", "" + parent.getItemAtPosition(position).toString())
            Toast.makeText(
                applicationContext,
                binding.stateselector.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setState()
            apiCity(binding.stateselector.text.toString())
        })
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
        // startService(Intent(this, LocationService::class.java))
    }
}
