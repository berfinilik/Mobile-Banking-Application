package com.berfinilik.bankingapplication.ui.sign

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextEPosta = binding.etEPosta
        val editTextPassword = binding.etPassword
        val buttonSignIn = binding.buttonSignIn
        val signUpBtn=binding.signUpBtn

        signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)

        }

        buttonSignIn.setOnClickListener {
            val ePosta = editTextEPosta.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            viewModel.loginUser(ePosta, password)
            viewModel.loginResult.observe(viewLifecycleOwner) { loginSuccessful ->
                if (loginSuccessful) {
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    currentUser?.let { user ->
                        val db = FirebaseFirestore.getInstance()
                        db.collection("users").document(user.uid).get()
                            .addOnSuccessListener { document ->
                                val hesapDurumu = document?.getBoolean("Hesap Durumu") ?: false
                                if (hesapDurumu) {
                                    findNavController().navigate(R.id.action_loginFragment_to_homePageFragment)
                                } else {
                                    Toast.makeText(requireContext(), "Hesabınız dondurulmuş.", Toast.LENGTH_LONG).show()
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.e(ContentValues.TAG, "Hesap durumu alınamadı: ", e)
                            }
                    }
                } else {
                    Toast.makeText(requireContext(), "Giriş Başarısız", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}