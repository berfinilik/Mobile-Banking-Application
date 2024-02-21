package com.berfinilik.bankingapplication.ui.cardmanagementscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berfinilik.bankingapplication.databinding.FragmentAddCardBinding
import com.berfinilik.bankingapplication.databinding.FragmentCardManagementBinding

class CardManagementFragment : Fragment() {
    private  var _binding:FragmentCardManagementBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentCardManagementBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}