package com.berfinilik.bankingapplication.ui.cardmanagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berfinilik.bankingapplication.Domain.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BankCardViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val docRef = firestore.collection("users").document(userId)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val hesapBakiyesi = document.getDouble("Hesap Bakiyesi") ?: 0.0
                        val kartNo = document.getString("Kart Numarası") ?: ""
                        _userData.value = UserData(hesapBakiyesi, kartNo)
                    } else {
                        _userData.value = UserData(0.0, "")
                    }
                }
                .addOnFailureListener { exception ->

                }
        }
    }
}
