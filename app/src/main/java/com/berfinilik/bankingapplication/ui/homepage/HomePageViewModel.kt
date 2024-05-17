package com.berfinilik.bankingapplication.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berfinilik.bankingapplication.Domain.Contact
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor() : ViewModel() {
    private val _contactList = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>> get() = _contactList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    init {
        fetchContacts()
    }
    private fun fetchContacts() {
        val currentUserUid=FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore
        val docRef = db.collection("users")

        viewModelScope.launch {
            try {
                val documents = docRef.get().await()
                val contactList = mutableListOf<Contact>()
                for (document in documents) {
                    if (document.id != currentUserUid) {
                        val name = document.getString("Ad") ?: ""
                        val surname = document.getString("Soyad") ?: ""
                        val picUrl = document.getString("picUrl") ?: ""
                        val iban = document.getString("IBAN") ?: ""

                        val contact = Contact(
                            name = name,
                            surname = surname,
                            gender = "",
                            email = "",
                            phoneNumber = "",
                            picUrl = picUrl,
                            iban = iban,
                            uid = ""
                        )
                        contactList.add(contact)

                    }
                }
                _contactList.value = contactList
            } catch (e: Exception) {
                _errorMessage.value = "Veri alınamadı: ${e.message}"
            }
        }
    }
}