package com.berfinilik.bankingapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berfinilik.bankingapplication.databinding.FragmentCardManagementBinding
import com.berfinilik.bankingapplication.databinding.FragmentSendMoneyBinding

class SendMoneyFragment : Fragment() {

    private lateinit var binding: FragmentSendMoneyBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentSendMoneyBinding.inflate(inflater,container,false)
        return binding.root

    }


}