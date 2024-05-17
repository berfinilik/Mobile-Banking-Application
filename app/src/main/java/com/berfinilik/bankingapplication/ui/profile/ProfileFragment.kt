package com.berfinilik.bankingapplication.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.berfinilik.bankingapplication.databinding.FragmentProfileBinding
import com.berfinilik.bankingapplication.utils.ChangeEmailDialog
import com.berfinilik.bankingapplication.utils.ChangePasswordDialog
import com.berfinilik.bankingapplication.utils.ChangePhoneNumberDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        binding.textView511.setOnClickListener {
            val changePasswordDialog = ChangePasswordDialog(requireContext()) { newPassword ->
                viewModel.changePassword(newPassword)
            }
            changePasswordDialog.showDialog()
        }
        binding.textView510.setOnClickListener {
            val ChangePhoneNumberDialog = ChangePhoneNumberDialog(requireContext()) { newPhoneNumber ->
                viewModel.changePhoneNumber(newPhoneNumber)
            }
            ChangePhoneNumberDialog.showDialog()
        }
        binding.textView51.setOnClickListener {
            val changeEmailDialog = ChangeEmailDialog(requireContext()) { newEmail ->
                viewModel.changeEmailAddress(newEmail)
            }
            changeEmailDialog.showDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.userData.observe(viewLifecycleOwner, { userData ->
            userData?.let { user ->
                binding.apply {
                    val fullName = "${user.name} ${user.surname}"
                    textViewAdSoyad.text = fullName
                    textViewEMail.text = user.email
                    textViewBakiye.text = user.phoneNumber
                    tvUserId.text = user.uid
                }
            }
        })
    }
}