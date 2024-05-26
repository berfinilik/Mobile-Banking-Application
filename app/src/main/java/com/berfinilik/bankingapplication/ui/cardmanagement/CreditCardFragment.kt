package com.berfinilik.bankingapplication.ui.cardmanagement

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.berfinilik.bankingapplication.databinding.FragmentCreditCardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class CreditCardFragment : Fragment() {
    private var _binding: FragmentCreditCardBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditCardBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        checkCreditCard()
    }
    private fun checkCreditCard() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val docRef = firestore.collection("users").document(userId)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val kartNumarasi = document.getString("Kredi Kart Numarası")
                        if (kartNumarasi == null) {
                            // Kredi kartı yok, diyalog aç
                            showDialog("Tanımlı bir kredi kartınız bulunmamaktadır.")
                        } else {
                            // Kredi kartı var, bilgileri yükle
                            loadCreditCardData()
                        }
                    } else {
                        showDialog("Kredi kartı bulunamadı.")
                    }
                }
                .addOnFailureListener { exception ->
                    showDialog("Bir hata oluştu. Lütfen tekrar deneyin.")
                }
        }
    }
    private fun showDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Tamam") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    private fun loadCreditCardData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val docRef = firestore.collection("users").document(userId)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val kartNumarasi = document.getString("Kredi Kart Numarası")
                        val toplamLimit = document.getDouble("Limit")
                        val kullanilabilirLimit = document.getDouble("Kullanılabilir Limit")

                        binding.creditCardNo.text = kartNumarasi
                        binding.toplamLimit.text = "Toplam Limit: ${toplamLimit.toString()} TL"
                        binding.kullanilabilirLimit.text = "Kalan Limit: ${kullanilabilirLimit.toString()} TL"
                    } else {
                        showDialog("Kredi kartı bulunamadı.")
                    }
                }
                .addOnFailureListener { exception ->
                    showDialog("Bir hata oluştu. Lütfen tekrar deneyin.")
                }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


