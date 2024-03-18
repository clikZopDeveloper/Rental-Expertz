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

class DashboardActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var rcNav: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

      //  val drawerLayout: DrawerLayout = binding.drawerLayout
        //  val navView: NavigationView = binding.navView
   //     val navBottomView: BottomNavigationView = binding.appBarMain.bottomNavView

        //val headerView: View = binding.navView.getHeaderView(0)

       // rcNav = headerView.findViewById<RecyclerView>(R.id.rcNaDrawer)

        binding.appBarMain.appbarLayout.ivNoti.setOnClickListener {
            GeneralUtilities.launchActivity(this, NotificationListActivity::class.java)
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
                    Toast.makeText(this,"Toast",Toast.LENGTH_SHORT).show()
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
}