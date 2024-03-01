package com.berfinilik.bankingapplication.ui.sendmoney

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SendMoneyViewModel @Inject constructor() :  ViewModel() {
    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun performTransfer(receiverIBAN: String, amount: Double, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mAuth.currentUser?.let { user ->

            val transferData = hashMapOf(
                "sender" to user.uid,
                "receiverIBAN" to receiverIBAN,
                "amount" to amount
            )

            db.collection("transfers")
                .add(transferData)
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
                .addOnFailureListener { exception ->
                    onFailure.invoke(exception.message ?: "Transfer işlemi sırasında bir hata oluştu.")
                }
        } ?: run {
            onFailure.invoke("Oturum açmadınız. Lütfen oturum açın.")
        }
    }
}