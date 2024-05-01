package com.example.rentalexpertz.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentalexpertz.Activity.DashboardActivity
import com.example.rentalexpertz.Adapter.AllTaskAdapter
import com.example.rentalexpertz.ApiHelper.ApiController
import com.example.rentalexpertz.ApiHelper.ApiResponseListner
import com.example.rentalexpertz.Model.AllTaskListBean
import com.example.rentalexpertz.Model.BaseResponseBean
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.GeneralUtilities
import com.example.rentalexpertz.Utills.PrefManager
import com.example.rentalexpertz.Utills.RvStatusClickListner
import com.example.rentalexpertz.Utills.SalesApp
import com.example.rentalexpertz.Utills.Utility

import com.example.rentalexpertz.databinding.FragmentStaffBinding
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants

class StaffFragment : Fragment(), ApiResponseListner {
    private lateinit var apiClient: ApiController
    private var _binding: FragmentStaffBinding? = null
    private lateinit var mAllAdapter: AllTaskAdapter
var type=""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStaffBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val titleText = (activity as DashboardActivity?)
        titleText?.setTitle("All Task")
        apiClient = ApiController(activity, this)
        apiAllTask()
        binding.swipeRefersh.setOnRefreshListener {
            apiAllTask()
            binding.swipeRefersh.isRefreshing = false

        }
        binding.fabAddTask.setOnClickListener {
            openAddTaskDialog()
        }
        return root
    }

    fun apiAllTask() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.GetTaskList, params)

    }

    fun apiAddTask(task: String) {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        params["task"] = task
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.GetAddTask, params)

    }

    fun apiUpdateTask(taskID: Int) {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        params["task_id"] = taskID.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.GetUpdateTask, params)

    }

    fun dialogUpdateTask(taskID: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to update task?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database

                apiUpdateTask(taskID)
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.GetAddTask) {
                val baseResponseBean = apiClient.getConvertIntoModel<BaseResponseBean>(
                    jsonElement.toString(),
                    BaseResponseBean::class.java
                )
                Toast.makeText(activity, baseResponseBean.msg, Toast.LENGTH_SHORT).show()
                apiAllTask()
            }
            if (tag == ApiContants.GetUpdateTask) {
                val baseResponseBean = apiClient.getConvertIntoModel<BaseResponseBean>(
                    jsonElement.toString(),
                    BaseResponseBean::class.java
                )
                Toast.makeText(activity, baseResponseBean.msg, Toast.LENGTH_SHORT).show()
                apiAllTask()
            }

            if (tag == ApiContants.GetTaskList) {
                var allTaskListBean = apiClient.getConvertIntoModel<AllTaskListBean>(
                    jsonElement.toString(),
                    AllTaskListBean::class.java
                )

                //
                if (allTaskListBean.error == false) {

                    handleAllTask(allTaskListBean.data)

                } else {
                    Toast.makeText(activity, allTaskListBean.msg, Toast.LENGTH_SHORT).show()
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

    fun handleAllTask(data: List<AllTaskListBean.Data>) {
        binding.rcTask.layoutManager =
            LinearLayoutManager(requireContext())
        mAllAdapter = AllTaskAdapter(requireActivity(), data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {
                dialogUpdateTask(pos)
            }
        })
        binding.rcTask.adapter = mAllAdapter
        mAllAdapter.notifyDataSetChanged()
        binding.tvPending.setOnClickListener {
            binding.tvPending.setTextColor(getResources().getColor(R.color.black));
            binding.tvSucess.setTextColor(getResources().getColor(R.color.white));
            type="Pending"
            if (data != null) {
                mAllAdapter.filter.filter(type)
            }
        }
        binding.tvSucess.setOnClickListener {
            binding.tvPending.setTextColor(getResources().getColor(R.color.white));
            binding.tvSucess.setTextColor(getResources().getColor(R.color.black));
            type="Complete"
            if (data != null) {
                mAllAdapter.filter.filter(type)
            }
        }

    /*    binding.edSearch.addTextChangedListener(object : TextWatcher {
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
                if (s.toString().trim { it <= ' ' }.length < 1) {
                    // ivClear.visibility = View.GONE
                } else {
                    //   ivClear.visibility = View.GONE
                }
            }
        })*/
        // rvMyAcFiled.isNestedScrollingEnabled = false
    }

    fun openAddTaskDialog() {
        val dialog: Dialog = GeneralUtilities.openBootmSheetDailog(
            R.layout.dialog_add_task, R.style.AppBottomSheetDialogTheme,
            requireActivity()
        )
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val btnAddTask = dialog.findViewById<TextView>(R.id.btnAddTask) as TextView
        val edAddTask = dialog.findViewById<EditText>(R.id.edAddTask) as EditText

        btnAddTask.setOnClickListener {
            if (TextUtils.isEmpty(edAddTask.text.toString())) {
                Toast.makeText(activity, "Please enter task", Toast.LENGTH_SHORT).show()

            } else {
                dialog.dismiss()
                apiAddTask(edAddTask.text.toString())
            }


        }
        ivClose.setOnClickListener { dialog.dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}