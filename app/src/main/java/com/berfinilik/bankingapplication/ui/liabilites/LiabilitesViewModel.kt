package com.berfinilik.bankingapplication.ui.liabilites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LiabilitesViewModel @Inject constructor() : ViewModel() {

    private val _kartNumarasi = MutableLiveData<String>()
    val kartNumarasi: LiveData<String> get() = _kartNumarasi

    private val _limit = MutableLiveData<Double>()
    val limit: LiveData<Double> get() = _limit

    private val _kullanilabilirlikLimit = MutableLiveData<Double>()
    val kullanilabilirlikLimit: LiveData<Double> get() = _kullanilabilirlikLimit

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        fetchKartBilgileri()
    }
    private fun fetchKartBilgileri() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        currentUser?.let { user ->
            val kartRef = db.collection("users").document(user.uid)
            kartRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val kartNumarasi = document.getString("Kredi Kart Numarası")
                        val limit = document.getDouble("Limit")
                        val kullanilabilirlikLimit = document.getDouble("Kullanılabilir Limit")
                        if (kartNumarasi != null && limit != null && kullanilabilirlikLimit != null) {
                            _kartNumarasi.value = kartNumarasi
                            _limit.value = limit
                            _kullanilabilirlikLimit.value = kullanilabilirlikLimit
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    _errorMessage.value = "Kart bilgileri alınamadı: ${exception.message}"
                }
        }
    }
}