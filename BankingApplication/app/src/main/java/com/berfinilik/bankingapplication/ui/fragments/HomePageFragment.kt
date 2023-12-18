package com.berfinilik.bankingapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentAddCardBinding
import com.berfinilik.bankingapplication.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentHomePageBinding.inflate(inflater,container,false)
        return binding.root
    }
}