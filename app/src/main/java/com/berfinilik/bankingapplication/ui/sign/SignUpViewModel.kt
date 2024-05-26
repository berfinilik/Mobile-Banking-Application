package com.berfinilik.bankingapplication.ui.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SignUpViewModel @Inject constructor(private val auth: FirebaseAuth,private val firestore: FirebaseFirestore) : ViewModel() {

    val signUpResult: MutableLiveData<Boolean> = MutableLiveData()
    fun signUpUser(
        email: String,
        password: String,
        ad: String,
        soyad: String,
        cinsiyet: String,
        telefonNumarasi: String
    ) {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val user = hashMapOf(
                            "Ad" to ad,
                            "Soyad" to soyad,
                            "Cinsiyet" to cinsiyet,
                            "E-posta adresi" to email,
                            "Telefon Numarası" to telefonNumarasi
                        )

                        firestore.collection("users")
                            .document(auth.currentUser!!.uid)
                            .set(user)
                            .addOnSuccessListener {
                                signUpResult.value = true
                            }
                            .addOnFailureListener { e ->
                                signUpResult.value = false
                                println("Error adding document: $e")
                            }
                    }
                    else {
                        signUpResult.value = false
                    }
                }
        } catch (e: Exception) {
            signUpResult.value = false
        }
    }
}