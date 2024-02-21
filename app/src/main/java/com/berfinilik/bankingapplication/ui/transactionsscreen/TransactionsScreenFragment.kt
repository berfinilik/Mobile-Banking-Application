package com.berfinilik.bankingapplication.ui.transactionsscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentTransactionsScreenBinding

class TransactionsScreenFragment : Fragment() {

    private  var _binding: FragmentTransactionsScreenBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTransactionsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}