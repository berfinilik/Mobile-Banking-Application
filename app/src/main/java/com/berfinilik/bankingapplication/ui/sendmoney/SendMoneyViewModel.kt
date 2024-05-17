package com.berfinilik.bankingapplication.ui.sendmoney

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.berfinilik.bankingapplication.Domain.Contact
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    fun transferMoney(
        senderUid: String,
        recipientUid: String,
        amount: Double,
        contact:Contact,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        Log.d(TAG, "Transfer işlemine başlanıyor. Gönderen UID: ${FirebaseAuth.getInstance().currentUser?.uid}, Alıcı UID: ${contact.uid}, Miktar: $amount")
        // Gönderenin bakiyesinden düş
        db.collection("users").document(senderUid)
            .update("Hesap Bakiyesi", FieldValue.increment(-amount))
            .addOnSuccessListener {
                // Alıcının bakiyesine ekle
                db.collection("users").document(recipientUid)
                    .update("Hesap Bakiyesi", FieldValue.increment(amount))
                    .addOnSuccessListener {
                        onSuccess()
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onFailure("Para gönderilirken bir hata oluştu: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                onFailure("Para gönderilirken bir hata oluştu: ${e.message}")
            }

    }
}