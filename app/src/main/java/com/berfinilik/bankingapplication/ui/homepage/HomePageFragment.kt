package com.berfinilik.bankingapplication.ui.homepage

import android.app.AlertDialog
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
import com.berfinilik.bankingapplication.Adapter.TransactionAdapter
import com.berfinilik.bankingapplication.Domain.Transaction
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentHomePageBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomePageViewModel by viewModels()
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var transactionAdapter: TransactionAdapter

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
        fetchTransactions()

        binding.ivSend.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToSendMoneyFragment)
            navigateToSendMoneyFragment()
        }

        binding.textViewSend.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToSendMoneyFragment)
        }
        binding.ivVarliklarim.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToAssetsFragment)
        }

        binding.textViewVarliklarim.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToAssetsFragment)
        }
        binding.textViewBorclarim.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToLiabilitesFragment)
        }
        binding.ivBorclar.setOnClickListener {
            findNavController().navigate(R.id.actionHomePageFragmentToLiabilitesFragment)
        }
        binding.ivVarliklarim.setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.white))
        binding.ivBorclar.setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.white))

        binding.viewFreeze.setOnClickListener {
            showFreezeAccountDialog()
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
                        val picUrl=document.getString("picUrl")?: ""

                        binding.textViewCardOwner.text = fullName
                        binding.textViewBakiye.text = "$hesapBakiyesi TL"
                        binding.textViewKartNumarasi.text = kartnumarasi
                        binding.textViewSonTarih.text = kartGecerlilikSuresi
                        binding.textView3.text = "Hoşgeldiniz $fullName"
                        Glide.with(requireContext()).load(picUrl).into(binding.imageView8)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Error getting sender information: ", e)
                }
        }
    }

    private fun fetchTransactions() {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore
        val transactionsCollectionRef = currentUserUid?.let { db.collection("users").document(it).collection("transactions") }

        transactionsCollectionRef?.get()?.addOnSuccessListener { querySnapshot ->
            val transactionList = mutableListOf<Transaction>()
            for (document in querySnapshot.documents) {
                val transactionType = document.getString("transaction type") ?: ""
                val amount = document.getDouble("transaction amount") ?: 0.0
                val date = document.getString("transaction date") ?: ""
                val picture = document.getString("transaction pic url") ?: ""

                val transaction = Transaction(transactionType, amount, date,picture)
                transactionList.add(transaction)
            }
            transactionAdapter.submitList(transactionList)
        }?.addOnFailureListener { exception ->
            Log.e(ContentValues.TAG, "Error getting transactions: ", exception)
            Toast.makeText(requireContext(), "Transactions couldn't be retrieved", Toast.LENGTH_SHORT).show()
        }
    }
    private fun showFreezeAccountDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Hesabı Dondur")
        builder.setMessage("Hesabınızı dondurmak istiyor musunuz?Bu işlemi gerçekleştirirseniz tekrar giriş yapabilmek için şubeye gitmeniz gerekir.")
        builder.setPositiveButton("Evet") { _, _ ->
            freezeAccount()
        }
        builder.setNegativeButton("Hayır") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter(emptyList())
        transactionAdapter = TransactionAdapter()
        binding.contactRecyclerView.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        binding.transactionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionAdapter
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

    private fun navigateToSendMoneyFragment() {
        val action = HomePageFragmentDirections.actionHomePageFragmentToSendMoneyFragment()
        findNavController().navigate(action)
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
                    findNavController().navigate(R.id.action_homePageFragment_to_loginFragment)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Hesap dondurulurken hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}