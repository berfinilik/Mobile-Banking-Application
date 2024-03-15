package com.berfinilik.bankingapplication.ui.cardmanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentCardManagementBinding
import com.berfinilik.bankingapplication.ui.cardmanagement.CardManagementAdapter

class CardManagementFragment : Fragment(), CardManagementAdapter.OnItemClickListener {
    private var _binding: FragmentCardManagementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemList = listOf("Swap", "Freeze", "Send", "Receive")

        val adapter = CardManagementAdapter(itemList, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onItemClick(position: Int) {
        val iconResId = when (position) {
            0 -> R.drawable.ic_swap
            1 -> R.drawable.ic_freeze
            2 -> R.drawable.ic_send
            3 -> R.drawable.ic_receive
            else -> R.drawable.ic_launcher_foreground
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
