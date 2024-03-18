package com.example.rentalexpertz.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rentalexpertz.Activity.DashboardActivity

import com.example.rentalexpertz.databinding.FragmentStaffBinding

class StaffFragment : Fragment() {

    private var _binding: FragmentStaffBinding? = null

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
        titleText?.setTitle("Staff")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}