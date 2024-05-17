package com.berfinilik.bankingapplication.ui.homepage

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.berfinilik.bankingapplication.Adapter.ContactAdapter
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomePageViewModel by viewModels()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()



        binding.ivSend.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToSendMoneyFragment)
        }

        binding.textViewSend.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToSendMoneyFragment)
        }
        binding.ivVarliklarim.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToVarliklarimFragment)
        }

        binding.textViewVarliklarim.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToVarliklarimFragment)
        }
        binding.textViewBorclarim.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToBorclarimFragment)
        }
        binding.ivBorclar.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToBorclarimFragment)
        }
        binding.ivVarliklarim.setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.white))
        binding.ivBorclar.setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.white))

        binding.viewFreeze.setOnClickListener {
            freezeAccount()
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        currentUser?.let { user ->
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name = document.getString("Ad") ?: ""
                        val surname = document.getString("Soyad") ?: ""
                        val fullName = "$name $surname"
                        val hesapBakiyesi = document.getDouble("Hesap Bakiyesi") ?: 0L
                        val kartnumarasi = document.getString("Kart Numarası") ?: ""
                        val kartGecerlilikSuresi=document.getString("kart son kullanma tarihi")?:""
                        binding.textViewCardOwner.setText(fullName)
                        binding.textViewBakiye.setText(hesapBakiyesi.toString()+"TL")
                        binding.textViewKartNumarasi.setText(kartnumarasi)
                        binding.textViewSonTarih.setText(kartGecerlilikSuresi)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Error getting sender information: ", e)
                }
        }
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter(emptyList())
        binding.contactRecyclerView.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun observeViewModel() {
        viewModel.contactList.observe(viewLifecycleOwner, Observer { contactList ->
            contactAdapter.updateData(contactList)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun freezeAccount() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        currentUser?.let { user ->
            db.collection("users").document(user.uid)
                .update("Hesap Durumu", false)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Hesap Donduruldu", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Hesap dondurulurken hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}