package com.berfinilik.bankingapplication.ui.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berfinilik.bankingapplication.ui.UserInfo.Address
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {
    private val _newAddress = MutableLiveData<Address>()
    val newAddress: LiveData<Address> = _newAddress

    fun createAddress(address: Address) {
        _newAddress.value = address
    }

    fun signUpUser(
        userName:String,
        userMail: String,
        userPass: String,
        userPhone: String,
        address: String,
        job1: String
    ) {

        auth.createUserWithEmailAndPassword(userMail, userPass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                 } else {
                 }
             }
    }
}
