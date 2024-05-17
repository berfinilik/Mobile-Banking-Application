package com.berfinilik.bankingapplication.ui.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    val loginResult: MutableLiveData<Boolean> = MutableLiveData()

    fun loginUser(eMail: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(eMail, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loginResult.value = true
                    } else {
                        loginResult.value = false
                    }
                }
        } catch (e: Exception) {
            loginResult.value = false
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