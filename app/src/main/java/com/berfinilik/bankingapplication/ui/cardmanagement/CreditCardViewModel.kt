package com.berfinilik.bankingapplication.ui.cardmanagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berfinilik.bankingapplication.Domain.CreditCardData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreditCardViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _creditCardData = MutableLiveData<CreditCardData>()
    val creditCardData: LiveData<CreditCardData> = _creditCardData

    fun checkCreditCard() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val docRef = firestore.collection("users").document(userId)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val kartNumarasi = document.getString("Kredi Kart Numarası")
                        if (kartNumarasi == null) {
                            _creditCardData.value = CreditCardData(null, null, null)
                        } else {
                            loadCreditCardData()
                        }
                    } else {
                        _creditCardData.value = CreditCardData(null, null, null)
                    }
                }
                .addOnFailureListener { exception ->
                    _creditCardData.value = CreditCardData(null, null, null)
                }
        }
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

                        _creditCardData.value = CreditCardData(kartNumarasi, toplamLimit, kullanilabilirLimit)
                    } else {
                        _creditCardData.value = CreditCardData(null, null, null)
                    }
                }
                .addOnFailureListener { exception ->
                    _creditCardData.value = CreditCardData(null, null, null)
                }
        }
    }
}

