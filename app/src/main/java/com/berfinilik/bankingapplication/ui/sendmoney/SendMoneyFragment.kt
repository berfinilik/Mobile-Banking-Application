package com.berfinilik.bankingapplication.ui.sendmoney

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.berfinilik.bankingapplication.databinding.FragmentSendMoneyBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SendMoneyFragment : Fragment() {

    private var _binding: FragmentSendMoneyBinding? = null
    private val binding get() = _binding!!

    private lateinit var editTextTransferMiktari: EditText
    private lateinit var buttonTransfer: Button
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSendMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextTransferMiktari = binding.editTextTransferMiktari
        buttonTransfer = binding.buttonTransfer
    }
    private fun validateInput(): Boolean {
        val transferAmount = editTextTransferMiktari.text.toString().toDoubleOrNull()
        if (transferAmount == null || transferAmount <= 0) {
            editTextTransferMiktari.error = "Geçerli bir miktar girin"
            return false
        }
        return true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

