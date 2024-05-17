package com.berfinilik.bankingapplication.ui.cardmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.berfinilik.bankingapplication.databinding.FragmentCardBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import com.berfinilik.bankingapplication.R


@AndroidEntryPoint
class CardFragment : Fragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)

        binding.linearLayoutBankCard.setOnClickListener {
            findNavController().navigate(R.id.action_cardFragment_to_bankCardFragment)
        }
        binding.linearLayoutCreditCard.setOnClickListener {
            findNavController().navigate(R.id.action_cardFragment_to_creditCardFragment)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
