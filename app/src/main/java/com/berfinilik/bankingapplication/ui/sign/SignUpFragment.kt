package com.berfinilik.bankingapplication.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentSignUpBinding
import com.berfinilik.bankingapplication.ui.UserInfo.Address

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var userNameText: EditText
    private lateinit var userPassText: EditText
    private lateinit var userMailText: EditText
    private lateinit var userPhoneText: EditText
    private lateinit var userAdress: TextView
    private lateinit var spinner: Spinner
    private lateinit var jobArrayAdapter: ArrayAdapter<String>
    private lateinit var jobs: Array<String>

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        userNameText = binding.root.findViewById(R.id.edittext_id_name_sign_up)
        userPassText = binding.root.findViewById(R.id.edittext_user_password_sign_up)
        userPhoneText = binding.root.findViewById(R.id.edittext_phone_sign_up)
        userMailText=binding.root.findViewById(R.id.edittext_mail_sign_up)
        userAdress= binding.root.findViewById(R.id.tvAdres)
        spinner = binding.root.findViewById(R.id.jobSpinner)


        userAdress.setOnClickListener {
            createAddress()
        }
        binding.buttonSignUp.setOnClickListener {
            signUp(it)
        }

        defineJobSpinner()
        return binding.root
    }

    private fun defineJobSpinner() {
        jobs = resources.getStringArray(R.array.jobs)
        jobArrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, jobs)
        spinner.adapter = jobArrayAdapter
    }

    private fun signUp(view: View) {
        val userName = userNameText.text.toString()
        val userMail=userMailText.text.toString()
        val userPass = userPassText.text.toString()
        val userPhone = userPhoneText.text.toString()
        val address=userAdress.text.toString()
        val job = spinner.selectedItem.toString()

        viewModel.signUpUser(userName,userMail,userPass,userPhone,address,job)

        Toast.makeText(requireContext(), "Kayıt işlemi başarılı!", Toast.LENGTH_SHORT).show()

    }

    private fun createAddress() {
        val ad = AlertDialog.Builder(requireContext())

        ad.setTitle("Adres")
        ad.setIcon(R.drawable.ic_address)
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.settings_address_popup, null)
        ad.setView(dialogView)
        ad.setPositiveButton("DEVAM") { dialog, which ->
            val street = dialogView.findViewById<TextView>(R.id.setting_address_street)
            val blockNo = dialogView.findViewById<TextView>(R.id.setting_address_block)
            val floor = dialogView.findViewById<TextView>(R.id.setting_address_floor)
            val houseNo = dialogView.findViewById<TextView>(R.id.setting_address_house)
            val country = dialogView.findViewById<TextView>(R.id.setting_address_country)
            val neighborhood = dialogView.findViewById<TextView>(R.id.setting_address_neigh)
            val town = dialogView.findViewById<TextView>(R.id.setting_address_town)
            val state = dialogView.findViewById<TextView>(R.id.setting_address_state)
            if (street.text.toString().isNotEmpty() && neighborhood.text.toString()
                    .isNotEmpty() && blockNo.text.toString().isNotEmpty() && floor.text.toString()
                    .isNotEmpty() && houseNo.text.toString().isNotEmpty() && town.text.toString()
                    .isNotEmpty() && state.text.toString().isNotEmpty() && country.text.toString()
                    .isNotEmpty()
            ) {
                val address = Address(
                    street.text.toString(),
                    neighborhood.text.toString(),
                    blockNo.text.toString().toInt(),
                    floor.text.toString().toInt(),
                    houseNo.text.toString().toInt(),
                    town.text.toString(),
                    state.text.toString(),
                    country.text.toString()
                )
                viewModel.createAddress(address)
            } else {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        ad.setNegativeButton("İptal") { dialog, which ->
            Toast.makeText(requireContext(), "İptal Edildi", Toast.LENGTH_SHORT).show()
        }
        ad.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}