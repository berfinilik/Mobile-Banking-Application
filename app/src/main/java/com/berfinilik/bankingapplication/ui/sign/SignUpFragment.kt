package com.berfinilik.bankingapplication.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignUp.setOnClickListener {
            val email = binding.edittextEmailSignUp.text.toString().trim()
            val password = binding.edittextUserPasswordSignUp.text.toString().trim()
            val ad = binding.edittextIdNameSignUp.text.toString().trim()
            val soyad = binding.edittextSurnameSignUp.text.toString().trim()
            val cinsiyet = getCinsiyet()
            val telefonNumarasi = binding.edittextPhoneSignUp.text.toString().trim()

            viewModel.signUpUser(email, password, ad, soyad, cinsiyet, telefonNumarasi)
        }

        viewModel.signUpResult.observe(viewLifecycleOwner) { signUpSuccessful ->
            if (signUpSuccessful) {
                Toast.makeText(requireContext(), "Kayıt başarılı", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            } else {
                Toast.makeText(requireContext(), "Kayıt başarısız", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getCinsiyet(): String {
        val radioGroup = binding.radioGroupCinsiyet
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        val selectedRadioButton = view?.findViewById<RadioButton>(selectedRadioButtonId)

        return selectedRadioButton?.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}