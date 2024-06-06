package com.example.rentalexpertz.Fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentalexpertz.Activity.*
import com.example.rentalexpertz.Adapter.CallSechduleAdapter
import com.example.rentalexpertz.Adapter.ChannelPartnerAdapter
import com.example.rentalexpertz.Adapter.DashAllLeadAdapter
import com.example.rentalexpertz.Adapter.DashTodayLeadAdapter
import com.example.rentalexpertz.Adapter.MissedFollowupAdapter
import com.example.rentalexpertz.Adapter.VisitSechduleAdapter
import com.example.rentalexpertz.ApiHelper.ApiController
import com.example.rentalexpertz.ApiHelper.ApiResponseListner
import com.example.rentalexpertz.Model.*
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.*
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants

class HomeFragment : Fragment(), ApiResponseListner {

    private lateinit var apiClient: ApiController
    private var _binding: com.example.rentalexpertz.databinding.FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = com.example.rentalexpertz.databinding.FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        val root: View = binding.root
        apiClient = ApiController(activity, this)

        val titleText = (activity as DashboardActivity?)
        titleText?.setTitle("Dashboard")

       ApiContants.movabalebutton(binding.fbAddArchitect, requireActivity())
      /*  binding.fbAddArchitect.setOnClickListener {
            startActivity(Intent(context, RecordingActivity::class.java))
          //  startActivity(Intent(context, RecordActivity::class.java))
        }*/


        binding.apply {
            refreshLayout.setOnRefreshListener {
                apiCallDashboard()
                refreshLayout.isRefreshing = false
            }
            binding.fbSearch.setOnClickListener {
                if (binding.edSearch.text.toString().isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Enter Search Key", Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(
                        Intent(context, SearchDataActivity::class.java).putExtra(
                            "searchKey",
                            binding.edSearch.text.toString()
                        )
                    )
                }
            }
            /* fbAddArchitect.setOnClickListener {
                 startActivity(Intent(requireActivity(), AddLeadActivity::class.java).putExtra("way","Add Lead"))
             }*/
            tvAllTask.setOnClickListener {
                (context as DashboardActivity).replaceFragment(StaffFragment())
            }
            /* tvAllTaskData.setOnClickListener {
                 (context as DashboardActivity).replaceFragment(StaffFragment())
             }*/
        }

        return root
    }

    fun apiAllGet() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getState, params)
    }

    fun apiCallDashboard() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        params["mobile"] = PrefManager.getString(ApiContants.mobileNumber, "")
        params["password"] = PrefManager.getString(ApiContants.password, "")
        // apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.dashboard, params)

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

            if (tag == ApiContants.dashboard) {
                var dashboardBean = apiClient.getConvertIntoModel<DashboardBean>(
                    jsonElement.toString(),
                    DashboardBean::class.java
                )

                Toast.makeText(activity, dashboardBean.msg, Toast.LENGTH_SHORT).show()
                if (dashboardBean.error == false) {
                    binding.apply {
                        tvAllTask.setOnClickListener {
                            tvAllLeads.setTextColor(getResources().getColor(R.color.black))
                            tvAllTask.setTextColor(getResources().getColor(R.color.text_color))

                            viewAllLTask.setBackgroundColor(getResources().getColor(R.color.colorPrimary))
                            viewAllLTask.visibility = View.VISIBLE
                            viewAllLLead.visibility = View.INVISIBLE
                            // handleRcTodayLeadDash(dashboardBean.data.todayLead)
                            (context as DashboardActivity).replaceFragment(StaffFragment())
                        }

                        tvAllLeads.setOnClickListener {
                            tvAllLeads.setTextColor(getResources().getColor(R.color.text_color))
                            tvAllTask.setTextColor(getResources().getColor(R.color.black))

                            viewAllLLead.setBackgroundColor(getResources().getColor(R.color.colorPrimary))
                            viewAllLLead.visibility = View.VISIBLE
                            viewAllLTask.visibility = View.INVISIBLE
                            handleRcAllLeadDash(dashboardBean.data.allLead)
                        }
                    }

                    //  dashboardBeanData= DashboardBean.Data.TodayLead
                    handleRcAllLeadDash(dashboardBean.data.allLead)
                    handleRcLeadCount(dashboardBean.data.allLeads)

                    binding.apply {

                        if (!dashboardBean.data.channelPartner.isNullOrEmpty()) {
                            handleChannelPartner(dashboardBean.data.channelPartner)
                        }

                        if (!dashboardBean.data.callSchedule.isNullOrEmpty()) {
                            handleCallSechdule(dashboardBean.data.callSchedule)
                        }
                        rlButton.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                            if (i == R.id.btn1) {
                                 /* btn1.setTextColor(requireActivity().resources.getColor(R.color.black))
                                  btn2.setTextColor(requireActivity().resources.getColor(R.color.black))
                                  btn3.setTextColor(requireActivity().resources.getColor(R.color.black))
             */

                                if (!dashboardBean.data.callSchedule.isNullOrEmpty()) {
                                    rcCallSechudle.visibility=View.VISIBLE
                                    handleCallSechdule(dashboardBean.data.callSchedule)

                                }else{
                                    rcCallSechudle.visibility=View.GONE
                                }
                            } else if (i == R.id.btn2) {
                                if (!dashboardBean.data.visitSchedule.isNullOrEmpty()) {
                                    rcCallSechudle.visibility=View.VISIBLE
                                    handleVistSechdule(dashboardBean.data.visitSchedule)
                                }else{
                                    rcCallSechudle.visibility=View.GONE
                                }
                            } else if (i == R.id.btn3) {
                                if (!dashboardBean.data.missedFollowup.isNullOrEmpty()) {
                                    rcCallSechudle.visibility=View.VISIBLE
                                    handleMissedFollowUp(dashboardBean.data.missedFollowup)
                                }else{
                                    rcCallSechudle.visibility=View.GONE
                                }

                            }
                        })


                    }
                }

            }

            if (tag == ApiContants.getState) {
                val stateBean = apiClient.getConvertIntoModel<StateBean>(
                    jsonElement.toString(),
                    StateBean::class.java
                )
                if (stateBean.error == false) {
                    SalesApp.stateList.clear()
                    SalesApp.stateList.addAll(stateBean.data)
                }
            }

        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }
    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(requireActivity(), errorMessage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun handleRcAllLeadDash(data: DashboardBean.Data.AllLead) {
        binding.rcDashboard.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        var mAdapter = DashAllLeadAdapter(requireActivity(), getMenusAllLead(data), object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {
                if (pos == 1) {
                    startActivity(
                        Intent(
                            context,
                            AddLeadActivity::class.java
                        ).putExtra("way", "Add Lead")
                    )
                } /*else if (pos == 10) {
                    startActivity(
                        Intent(
                            context,
                            AllLeadActivity::class.java
                        ).putExtra("leadStatus", "CONVERTED").putExtra("conversion", "Partial")
                    )
                } else if (pos == 11) {
                    startActivity(
                        Intent(
                            context,
                            AllLeadActivity::class.java
                        ).putExtra("leadStatus", "CONVERTED").putExtra("conversion", "Completed")
                    )
                }*/ else {
                    startActivity(
                        Intent(
                            context,
                            AllLeadActivity::class.java
                        ).putExtra("leadStatus", status)
                    )
                }
            }
        })
        binding.rcDashboard.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false
    }

    private fun getMenusAllLead(data: DashboardBean.Data.AllLead): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        //  menuList.add(MenuModelBean(1, "Add Lead", data.newLeads.toString(), R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "new lead", data.newLeads.toString(), R.drawable.ic_dashbord))
        menuList.add(
            MenuModelBean(
                3,
                "pending",
                data.pendingLeads.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                6,
                "processed",
                data.processingLeads.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                4,
                "converted",
                data.convertedLeads.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                5,
                "call scheduled",
                data.callScheduled.toString(),
                R.drawable.ic_dashbord
            )
        )

        menuList.add(
            MenuModelBean(
                8,
                "visit scheduled",
                data.visitScheduled.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                9,
                "visit done",
                data.visitDone.toString(),
                R.drawable.ic_dashbord
            )
        )

        /*
                 menuList.add(
                     MenuModelBean(
                         11,
                         "booked",
                         data.convertedLeads.toString(),
                         R.drawable.ic_dashbord
                     )
                 )*/
        /*
          menuList.add(
                     MenuModelBean(
                         11,
                         "completed",
                         data.convertedLeads.toString(),
                         R.drawable.ic_dashbord
                     )
                 )

                  menuList.add(
                     MenuModelBean(
                         11,
                         "cancelled",
                         data.convertedLeads.toString(),
                         R.drawable.ic_dashbord
                     )
                 )
        */

        menuList.add(
            MenuModelBean(
                12,
                "not interested",
                data.notInterested.toString(),
                R.drawable.ic_dashbord
            )
        )

        menuList.add(
            MenuModelBean(
                13,
                "not picked",
                data.notPicked.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                14,
                "wrong number",
                data.wrongNumber.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                15,
                "not reachable",
                data.notReachable.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                16,
                "channel partner",
                data.channelPartner.toString(),
                R.drawable.ic_dashbord
            )
        )

        return menuList
    }

    fun handleRcLeadCount(data: DashboardBean.Data.AllLeads) {
        binding.rcLeadType.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        var mAdapter = DashAllLeadAdapter(requireActivity(), getMenusLeadCount(data), object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {
            /*    if (pos == 1) {
                    startActivity(
                        Intent(
                            context,
                            AddLeadActivity::class.java
                        ).putExtra("way", "Add Lead")
                    )
                }*/
            }
        })
        binding.rcLeadType.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false
    }

    private fun getMenusLeadCount(data: DashboardBean.Data.AllLeads): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        menuList.add(MenuModelBean(1, "Total Leads", data.allLead.totalLeads.toString(), R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "Today Leads", data.todayLead.totalLeads.toString(), R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(3, "This Month Lead", data.thisMonthLead.totalLeads.toString(), R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(4, "Future Lead", data.futureLead.totalLeads.toString(), R.drawable.ic_dashbord))

        return menuList
    }

    fun handleCallSechdule(data: List<DashboardBean.Data.CallSchedule>) {
        binding.rcCallSechudle.layoutManager = LinearLayoutManager(requireContext())
        var mAdapter = CallSechduleAdapter(requireActivity(), data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        binding.rcCallSechudle.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false
        mAdapter.notifyDataSetChanged()
    }

    fun handleVistSechdule(data: List<DashboardBean.Data.VisitSchedule>) {
        binding.rcCallSechudle.layoutManager =
            LinearLayoutManager(requireContext())
        var mAdapter = VisitSechduleAdapter(requireActivity(), data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        binding.rcCallSechudle.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false
        mAdapter.notifyDataSetChanged()

    }

    fun handleMissedFollowUp(data: List<DashboardBean.Data.MissedFollowup>) {
        binding.rcCallSechudle.layoutManager =
            LinearLayoutManager(requireContext())
        var mAdapter = MissedFollowupAdapter(requireActivity(), data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        binding.rcCallSechudle.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false
        mAdapter.notifyDataSetChanged()

    }

    fun handleChannelPartner(data: List<DashboardBean.Data.ChannelPartner>) {
        binding.rcChannelPartner.layoutManager =
            LinearLayoutManager(requireContext())
        var mAdapter = ChannelPartnerAdapter(requireActivity(), data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        binding.rcChannelPartner.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false
        mAdapter.notifyDataSetChanged()

    }

    fun handleRcTodayLeadDash(data: DashboardBean.Data.TodayLead) {
        binding.rcDashboard.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        var mAdapter = DashTodayLeadAdapter(requireActivity(), getMenusTodayLead(data), object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {
                if (pos == 1) {
                    startActivity(
                        Intent(
                            context,
                            AddLeadActivity::class.java
                        ).putExtra("way", "Add Lead")
                    )
                } /*else if (pos == 10) {
                    startActivity(
                        Intent(
                            context,
                            AllLeadActivity::class.java
                        ).putExtra("leadStatus", "CONVERTED").putExtra("conversion", "Partial")
                    )
                } else if (pos == 11) {
                    startActivity(
                        Intent(
                            context,
                            AllLeadActivity::class.java
                        ).putExtra("leadStatus", "CONVERTED").putExtra("conversion", "Completed")
                    )
                }*/ else {
                    startActivity(
                        Intent(
                            context,
                            AllLeadActivity::class.java
                        ).putExtra("leadStatus", status)
                    )
                }
            }
        })
        binding.rcDashboard.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    private fun getMenusTodayLead(data: DashboardBean.Data.TodayLead): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        // menuList.add(MenuModelBean(1, "Add Lead", data.newLeads.toString(), R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "new lead", data.newLeads.toString(), R.drawable.ic_dashbord))
        menuList.add(
            MenuModelBean(
                3,
                "pending",
                data.pendingLeads.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                6,
                "processed",
                data.processingLeads.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                4,
                "converted",
                data.convertedLeads.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                5,
                "call scheduled",
                data.callScheduled.toString(),
                R.drawable.ic_dashbord
            )
        )

        menuList.add(
            MenuModelBean(
                8,
                "visit scheduled",
                data.visitScheduled.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                9,
                "visit done",
                data.visitDone.toString(),
                R.drawable.ic_dashbord
            )
        )

        /*
                 menuList.add(
                     MenuModelBean(
                         11,
                         "booked",
                         data.convertedLeads.toString(),
                         R.drawable.ic_dashbord
                     )
                 )*/
        /*
          menuList.add(
                     MenuModelBean(
                         11,
                         "completed",
                         data.convertedLeads.toString(),
                         R.drawable.ic_dashbord
                     )
                 )

                  menuList.add(
                     MenuModelBean(
                         11,
                         "cancelled",
                         data.convertedLeads.toString(),
                         R.drawable.ic_dashbord
                     )
                 )
        */

        menuList.add(
            MenuModelBean(
                12,
                "not interested",
                data.notInterested.toString(),
                R.drawable.ic_dashbord
            )
        )

        menuList.add(
            MenuModelBean(
                13,
                "not picked",
                data.notPicked.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                14,
                "wrong number",
                data.wrongNumber.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                15,
                "not reachable",
                data.notReachable.toString(),
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                16,
                "channel partner",
                data.channelPartner.toString(),
                R.drawable.ic_dashbord
            )
        )

        return menuList
    }

    fun callPGURL(url: String) {
        Log.d("weburl", url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        apiAllGet()
        apiCallDashboard()
    }

}