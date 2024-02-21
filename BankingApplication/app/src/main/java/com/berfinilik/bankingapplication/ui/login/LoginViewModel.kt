package com.berfinilik.bankingapplication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    fun createUser(){
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword("deneme2@gmail.com","12345678").addOnCompleteListener {
                println("sonuc ${it.result}")
            }
        }
    }
}