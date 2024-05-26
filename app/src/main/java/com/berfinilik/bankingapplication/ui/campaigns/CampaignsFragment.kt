package com.berfinilik.bankingapplication.ui.campaigns

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.berfinilik.bankingapplication.Domain.KampanyaModel
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.FragmentCampaignsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignsFragment : Fragment() {

    private var _binding: FragmentCampaignsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CampaignsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCampaignsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedKampanya.observe(viewLifecycleOwner, Observer { kampanya ->
            kampanya?.let {
                showCustomDialog(it.title, it.message, it.imageResId)
            }
        })


        binding.cardView1.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    R.string.eft_masraf_title,
                    R.string.eft_masraf_desc,
                    R.drawable.sifirmasraf

                )
            )
        }
        binding.cardView2.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    R.string.eft_indirim_title,
                    R.string.eft_indirim_desc,
                    R.drawable.yuzdeellindirim
                )
            )
        }
        binding.cardView3.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    R.string.havale_masraf_title,
                    R.string.havale_masraf_desc,
                    R.drawable.dijitalkanalsifirmasraf
                )
            )
        }
        binding.cardView4.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    R.string.harca_bankkart_title,
                    R.string.harca_bankkart_desc,
                    R.drawable.bankkartlira
                )
            )
        }
        binding.cardView6.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    R.string.vadeli_mevduat_title,
                    R.string.vadeli_mevduat_desc,
                    R.drawable.vadelimevduataekfaiz
                )
            )
        }
        binding.cardView7.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    R.string.aidatsiz_kredi_karti_title,
                    R.string.aidatsiz_kredi_karti_desc,
                    R.drawable.aidatsizkredikarti
                )
            )
        }
        binding.cardView5.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    R.string.taksitlendirme_imkani_title,
                    R.string.taksitlendirme_imkani_desc,
                    R.drawable.taksit
                )
            )
        }
        binding.cardView8.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    R.string.kamu_atm_limit_title,
                    R.string.kamu_atm_limit_desc,
                    R.drawable.atm
                )
            )
        }
    }


    private fun showCustomDialog(titleResId: Int, messageResId: Int, imageResId: Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_info_layout)

        val dialogTitle = dialog.findViewById<TextView>(R.id.textViewDialogTitle)
        val dialogText = dialog.findViewById<TextView>(R.id.textViewDialogInfo)
        val closeButton = dialog.findViewById<ImageView>(R.id.closeButton)
        val dialogImageView = dialog.findViewById<ImageView>(R.id.dialogImageView)

        dialogTitle.text = getString(titleResId)
        dialogText.text = getString(messageResId)
        dialogImageView.setImageResource(imageResId)

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}