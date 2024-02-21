package com.berfinilik.bankingapplication.ui.loginscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentLoginBinding
import com.berfinilik.bankingapplication.databinding.FragmentTransactionsScreenBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextPhoneNumber = binding.etPhoneNumber
        val editTextPassword = binding.etSifre
        val buttonLogin = binding.btnGiris

        buttonLogin.setOnClickListener {
            val phoneNumber = editTextPhoneNumber.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            loginUser(phoneNumber, password)
        }
    }

    private fun loginUser(phoneNumber: String, password: String) {
        mAuth.signInWithEmailAndPassword(phoneNumber, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                } else {

                    Toast.makeText(requireContext(), "Giriş başarısız: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
