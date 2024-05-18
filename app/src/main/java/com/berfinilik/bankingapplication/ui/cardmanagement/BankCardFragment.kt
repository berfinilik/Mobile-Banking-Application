package com.berfinilik.bankingapplication.ui.cardmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.berfinilik.bankingapplication.databinding.FragmentBankCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankCardFragment : Fragment() {
    private var _binding: FragmentBankCardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BankCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankCardBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userData.observe(viewLifecycleOwner, Observer { userData ->
            if (userData.kartNo.isNullOrEmpty()) {
                Toast.makeText(context, "Tanımlı banka kartınız bulunmamaktadır.", Toast.LENGTH_SHORT).show()
            } else {
                binding.cardNo.text = userData.kartNo
                binding.bakiye.text = "Kullanılabilir Bakiye: ${userData.hesapBakiyesi} TL"
            }
        })
        viewModel.loadUserData()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
