package com.berfinilik.bankingapplication.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berfinilik.bankingapplication.Domain.Contact
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _userData = MutableLiveData<Contact>()
    val userData: LiveData<Contact> = _userData
    init {
        getUserData()
    }
    fun changePassword(newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let { currentUser ->
            if (newPassword.length >= 6) {
                currentUser.updatePassword(newPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Şifre başarıyla güncellendi.")

                        } else {
                            Log.e(TAG, "Şifre güncelleme hatası: ${task.exception}")

                        }
                    }
            } else {
                Log.e(TAG, "Şifre geçersiz. En az 6 karakter olmalı.")
            }
        }
    }

    fun changePhoneNumber(newPhoneNumber: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val userRef = db.collection("users").document(user.uid)

            userRef.update("Telefon Numarası", newPhoneNumber)
                .addOnSuccessListener {
                    Log.d(TAG, "Telefon numarası başarıyla güncellendi.")

                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Telefon numarası güncelleme hatası: $exception")

                }
        }

    }

    fun changeEmailAddress(newEmailAddress: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            user.updateEmail(newEmailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userRef = db.collection("users").document(user.uid)
                        userRef.update("E-posta Adresi", newEmailAddress)
                            .addOnSuccessListener {
                                Log.d(TAG, "E-posta adresi başarıyla güncellendi.")
                            }
                            .addOnFailureListener { exception ->
                                Log.e(TAG, "E-posta adresi güncelleme hatası: $exception")
                            }
                    } else {
                        Log.e(TAG, "E-posta adresi güncelleme hatası: ${task.exception}")
                    }
                }
        }
    }

    private fun getUserData() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val userRef = db.collection("users").document(user.uid)

            userRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val picUrl=document.getString("picUrl")?:""
                        val name = document.getString("Ad") ?: ""
                        val surname = document.getString("Soyad") ?: ""
                        val email = user.email ?: ""
                        val phoneNumber = document.getString("Telefon Numarası") ?: ""
                        val uid = user.uid

                        val user = Contact(name, surname, "", email, phoneNumber, picUrl, "", uid)
                        _userData.value = user
                    } else {
                        // Kullanıcı dokümanı bulunamadı veya yoksa yapılacak işlemler
                    }
                }
                .addOnFailureListener { exception ->
                    // Firestore'dan veri alma hatası durumunda yapılacak işlemler
                }
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}