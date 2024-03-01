import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentLoginBinding
import com.berfinilik.bankingapplication.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextPhoneNumber = binding.etTelNumber
        val editTextPassword = binding.etPassword
        val buttonLogin = binding.buttonSignIn

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
                    findNavController().navigate(R.id.action_loginFragment_to_homePageFragment)
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

