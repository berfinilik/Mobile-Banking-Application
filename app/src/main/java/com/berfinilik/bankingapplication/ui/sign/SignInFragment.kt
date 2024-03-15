package com.berfinilik.bankingapplication.ui.sign
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentSignInBinding

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
        val buttonSignUp=binding.buttonSignUp

        buttonSignIn.setOnClickListener {
            val ePosta = editTextEPosta.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (viewModel.loginUser(ePosta, password)) {
                findNavController().navigate(R.id.action_loginFragment_to_homePageFragment)
            } else {
               Toast.makeText(requireContext(),"Başarısız",Toast.LENGTH_LONG).show()
            }
        }
        buttonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}