package com.berfinilik.bankingapplication.ui.sendmoney

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.berfinilik.bankingapplication.databinding.FragmentSendMoneyBinding
import com.berfinilik.bankingapplication.Domain.Contact
import com.berfinilik.bankingapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendMoneyFragment : Fragment() {

    private var _binding: FragmentSendMoneyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SendMoneyViewModel by viewModels()

    private var selectedContact: Contact? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSendMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        currentUser?.let { user ->
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val senderName = document.getString("Ad") ?: ""
                        val senderSurname = document.getString("Soyad") ?: ""
                        val senderIban = document.getString("IBAN") ?: ""
                        val senderHesapBakiyesi = document.getDouble("Hesap Bakiyesi") ?: 0L
                        val senderFullName = "$senderName $senderSurname"

                        binding.gondericiAdSoyad.setText(senderFullName)
                        binding.gondericiHesapBakiyesi.setText(senderHesapBakiyesi.toString())
                        binding.gondericiIban.setText(senderIban)
                    }
                }
        }
        arguments?.let {
            val selectedContactName = it.getString("selected_contact_name", "")
            val selectedContactSurname = it.getString("selected_contact_surname", "")
            val selectedContactIban = it.getString("selected_contact_iban", "")
            val selectedContactUid = it.getString("selected_contact_uid", "")
            val selectedContactGender = it.getString("selected_contact_gender", "")
            val selectedContactEmail = it.getString("selected_contact_email", "")
            val selectedContactPhoneNumber = it.getString("selected_contact_phone_number", "")
            val selectedContactPicUrl = it.getString("selected_contact_pic_url", "")

            val selectedContactFullName="$selectedContactName $selectedContactSurname"

            selectedContact = Contact(
                name = selectedContactName,
                surname = selectedContactSurname,
                gender = selectedContactGender,
                email = selectedContactEmail,
                phoneNumber = selectedContactPhoneNumber,
                picUrl = selectedContactPicUrl,
                iban = selectedContactIban,
                uid = selectedContactUid
            )

            binding.editTextAliciAdSoyad.setText(selectedContactFullName)
            binding.editTextAliciIban.setText(selectedContactIban)
            binding.textViewKisi.setText(selectedContactFullName)
        }

        val amountEditText = binding.editTextTransferMiktari
        val sendButton = binding.buttonTransfer

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.actionSendMoneyFragmentToHomePageFragment)
        }

        sendButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDoubleOrNull()

            if (amount == null || amount <= 0) {
                Toast.makeText(activity, "Geçerli bir miktar giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            selectedContact?.let { contact ->

                try {
                    viewModel.transferMoney(
                        senderUid = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                        recipientUid = contact.uid ?:"",
                        amount = amount,
                        contact=contact,
                        onSuccess = {
                            Toast.makeText(
                                activity,
                                "Para transferi gerçekleşti",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFailure = { errorMessage ->
                            Toast.makeText(
                                activity,
                                errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Transfer işlemi sırasında bir hata oluştu: ${e.message}", e)
                    Toast.makeText(
                        activity,
                        "Transfer işlemi sırasında bir hata oluştu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}