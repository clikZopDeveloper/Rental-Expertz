package com.example.rentalexpertz.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalexpertz.Adapter.AllStatusAdapter
import com.example.rentalexpertz.Adapter.CommonFieldDrawerAdapter
import com.example.rentalexpertz.ApiHelper.ApiController
import com.example.rentalexpertz.ApiHelper.ApiResponseListner
import com.example.rentalexpertz.Fragment.HomeFragment
import com.example.rentalexpertz.Fragment.SettingFragment
import com.example.rentalexpertz.Fragment.StaffFragment
import com.example.rentalexpertz.Model.*
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.*
import com.example.rentalexpertz.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import java.util.Locale

class DashboardActivity : AppCompatActivity() , ApiResponseListner{
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var apiClient: ApiController

    private lateinit var binding: ActivityMainBinding
    lateinit var rcNav: RecyclerView
    private var currentLoc: String? = null
    private val permissionId = 2
    var list: List<Address>? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
      //  val drawerLayout: DrawerLayout = binding.drawerLayout
        //  val navView: NavigationView = binding.navView
   //     val navBottomView: BottomNavigationView = binding.appBarMain.bottomNavView

        //val headerView: View = binding.navView.getHeaderView(0)

       // rcNav = headerView.findViewById<RecyclerView>(R.id.rcNaDrawer)
        binding.appBarMain.appbarLayout.ivAddLead.visibility=View.VISIBLE
        binding.appBarMain.appbarLayout.ivNoti.visibility=View.VISIBLE
        binding.appBarMain.appbarLayout.ivNoti.setOnClickListener {
            GeneralUtilities.launchActivity(this, NotificationListActivity::class.java)
        }
        binding.appBarMain.appbarLayout.ivAddLead.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    AddLeadActivity::class.java
                ).putExtra("way", "Add Lead")
            )

        }

      //  val navController = findNavController(R.id.nav_host_fragment_activity_main)

        binding.appBarMain.appbarLayout.ivMenu.setOnClickListener {
      //      drawerLayout.open()
        }

     //   handleRecyclerDrawer()
      //  navBottomView.setOnNavigationItemSelectedListener(mBottomNavigation)

        Log.d("token>>>>>", PrefManager.getString(ApiContants.AccessToken, ""))

    //    Log.d("asdad", PrefManager.getString(ApiContants.dayStatus, ""))

        //  setupActionBarWithNavController(navController, appBarConfiguration)
      //  navBottomView.setupWithNavController(navController)

        binding.appBarMain.appbarLayout.switchDayStart.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked) {
            //    binding.appBarMain.appbarLayout.switchDayStart.text = "Day Start"
                getLocation()
            //    apiCallDayStatus(ApiContants.startDay)
                PrefManager.putString(ApiContants.dayStatus, "start")
            } else {
             //   binding.appBarMain.appbarLayout.switchDayStart.text = "Day End"
                getLocation()

              //  apiCallDayStatus(ApiContants.endDay)
                PrefManager.putString(ApiContants.dayStatus, "end")
            }

        })


        val bottomNavigationView =
            findViewById<View>(R.id.bottom_nav_view) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(mBottomNavigation)
        GeneralUtilities.goToFragment(
            this,
            HomeFragment(),
            R.id.container,
            true
        )

    }
    fun setTitle(title: kotlin.String) {
        binding.appBarMain.appbarLayout.tvTitle.text = title
    }
    fun apiCallDayStatus(dayStatus: String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val params = Utility.getParmMap()
        params["last_location"] = "${list?.get(0)?.latitude},${list?.get(0)?.longitude}"
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(dayStatus, params)

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()

            if (tag == ApiContants.startDay) {
                val dayStatusBean = apiClient.getConvertIntoModel<StartDayBean>(
                    jsonElement.toString(),
                    StartDayBean::class.java
                )
                if (dayStatusBean.error == false) {
                    Utility.showSnackBar(this, dayStatusBean.msg)
                }
            }

            if (tag == ApiContants.endDay) {
                val dayStatusBean = apiClient.getConvertIntoModel<EndDayBean>(
                    jsonElement.toString(),
                    EndDayBean::class.java
                )
                if (dayStatusBean.error == false) {
                    Utility.showSnackBar(this, dayStatusBean.msg)
                }
            }


        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }

    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        // Toast.makeText(this, "4", Toast.LENGTH_SHORT).show()
        Utility.showSnackBar(this, errorMessage)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            // only show dialog while there's back stack entry
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            // or just go back to main activity
            super.onBackPressed();
        }
    }

    private val mBottomNavigation =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    //mRecyclerView.releasePlayer();
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_allleads -> {
                 //   Toast.makeText(this,"Toast",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,AllLeadActivity::class.java).putExtra("leadStatus",""))
                    // replaceFragment(RequrimentFragment())
                    // mRecyclerView.releasePlayer();
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_staff -> {
                           replaceFragment(StaffFragment())

                    //   mRecyclerView.releasePlayer();
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_setting -> {
                    replaceFragment(SettingFragment())
                    //   mRecyclerView.releasePlayer();
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    fun handleRecyclerDrawer() {
        rcNav.layoutManager = LinearLayoutManager(this)
        var mAdapter = CommonFieldDrawerAdapter(this, getMenus(), object :
            RvClickListner {
            override fun clickPos(pos: Int) {
            }
        })
        rcNav.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    private fun getMenus(): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        menuList.add(MenuModelBean(0, "Lead", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(1, "Task", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "My Customers", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(3, "Template", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(4, "Chart", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(5, "Staff", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(6, "Sticky Notes", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(7, "Holidays", "", R.drawable.ic_dashbord))

        return menuList
    }

    fun replaceFragment( fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
*/
    override fun onDestroy() {
        super.onDestroy()
        // Start the LocationService when the app is closed
     //   startService(Intent(this, LocationService::class.java))
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        list =

                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        Log.d("zxxzv", "Lat" + Gson().toJson(list?.get(0)?.latitude))
                        Log.d("zxxzv", "Long" + Gson().toJson(list?.get(0)?.longitude))
                        Log.d("zxxzv", Gson().toJson(list?.get(0)?.countryName))
                        Log.d("zxxzv", Gson().toJson(list?.get(0)?.locality))
                        Log.d("zxxzv", Gson().toJson(list?.get(0)?.getAddressLine(0)))

                        currentLoc = list?.get(0)?.getAddressLine(0)
                        /*    mainBinding.apply {
                                tvLatitude.text = "Latitude\n${list[0].latitude}"
                                tvLongitude.text = "Longitude\n${list[0].longitude}"
                                tvCountryName.text = "Country Name\n${list[0].countryName}"
                                tvLocality.text = "Locality\n${list[0].locality}"
                                tvAddress.text = "Address\n${list[0].getAddressLine(0)}"
                            }*/
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        } else {
            //  checkPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}