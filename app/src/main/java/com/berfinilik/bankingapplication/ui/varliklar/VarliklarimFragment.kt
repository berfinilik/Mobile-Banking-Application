package com.berfinilik.bankingapplication.ui.varliklar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.berfinilik.bankingapplication.databinding.FragmentVarliklarimBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VarliklarimFragment : Fragment() {
    private var _binding: FragmentVarliklarimBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VarliklarimViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVarliklarimBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.hesapBakiyesi.observe(viewLifecycleOwner, Observer { hesapBakiyesi ->
            binding.textViewToplam.text = "$hesapBakiyesi TL"
            binding.textViewToplamTutar.text = "Toplam Tutar: $hesapBakiyesi TL"
            binding.textViewToplamCariTl.text = "$hesapBakiyesi TL"

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}