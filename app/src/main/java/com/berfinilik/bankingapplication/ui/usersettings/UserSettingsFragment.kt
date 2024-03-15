package com.berfinilik.bankingapplication.ui.usersettings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berfinilik.bankingapplication.databinding.FragmentUserSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class UserSettingsFragment : Fragment() {

    private  var _binding: FragmentUserSettingsBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentUserSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}