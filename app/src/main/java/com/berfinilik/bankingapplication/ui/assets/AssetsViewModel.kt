package com.berfinilik.bankingapplication.ui.assets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AssetsViewModel @Inject constructor() : ViewModel() {
    private val _hesapBakiyesi = MutableLiveData<Double>()
    val hesapBakiyesi: LiveData<Double> get() = _hesapBakiyesi

    init {
        loadHesapBakiyesi()
    }

    private fun loadHesapBakiyesi() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        currentUser?.let { user ->
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val hesapBakiyesi = document.getDouble("Hesap Bakiyesi") ?: 0.0
                        _hesapBakiyesi.postValue(hesapBakiyesi)
                    }
                }
                .addOnFailureListener { e ->
                }
        }
    }
}