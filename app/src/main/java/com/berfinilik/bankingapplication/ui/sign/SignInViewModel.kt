package com.berfinilik.bankingapplication.ui.sign

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    fun loginUser(eMail: String, password: String): Boolean {
        return try {
            val result = auth.signInWithEmailAndPassword(eMail, password).isSuccessful
            result
        } catch (e: Exception) {
            false
        }
    }

    fun createUser() {
        auth.createUserWithEmailAndPassword("deneme2@gmail.com", "12345678")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Kullanıcı başarıyla oluşturuldu")
                } else {
                    println("Kullanıcı oluşturulurken hata oluştu: ${task.exception}")
                }
            }
    }
}

