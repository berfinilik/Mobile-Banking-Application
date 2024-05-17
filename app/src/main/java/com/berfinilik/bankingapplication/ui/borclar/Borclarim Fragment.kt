package com.berfinilik.bankingapplication.ui.borclar

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.berfinilik.bankingapplication.databinding.FragmentBorclarimBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BorclarimFragment : Fragment() {

    private var _binding: FragmentBorclarimBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BorclarimViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBorclarimBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.kartNumarasi.observe(viewLifecycleOwner, Observer { kartNumarasi ->
            binding.textViewKrediKartNo.text = kartNumarasi
        })

        viewModel.limit.observe(viewLifecycleOwner, Observer { limit ->
            val kullanilabilirlikLimit = viewModel.kullanilabilirlikLimit.value ?: 0.0
            val borc = limit - kullanilabilirlikLimit
            binding.textViewBorc.text = "$borc TL"
            binding.textViewKredi.text = "$borc TL"
            binding.borclarimTextView.text = "Toplam Tutar: $borc TL"
            binding.textViewToplamBorc.text = "$borc TL"
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}