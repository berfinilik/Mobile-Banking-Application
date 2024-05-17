package com.berfinilik.bankingapplication.ui.kampanyalar

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
import com.berfinilik.bankingapplication.databinding.FragmentKampanyalarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KampanyalarFragment : Fragment() {

    private var _binding: FragmentKampanyalarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: KampanyalarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKampanyalarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'deki seçili kampanyayı gözlemleyin ve dialog gösterin
        viewModel.selectedKampanya.observe(viewLifecycleOwner, Observer { kampanya ->
            kampanya?.let {
                showCustomDialog(it.title, it.message, it.imageResId)
            }
        })

        // CardView'lara tıklanınca ViewModel'deki kampanyayı seçin
        binding.cardView1.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    "EFT/FAST/Havale işlemlerinde sıfır masraf!",
                    "Dijital kanallardan gerçekleştirilen EFT/FAST/Havale işlemlerinde sıfır masraf!",
                    R.drawable.sifirmasraf
                )
            )
        }
        binding.cardView2.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    "EFT/FAST işlemlerinde %50 indirim",
                    "İnternet ve mobil bankacılıktan gerçekleştirilen EFT/FAST işlem masraflarında %50 oranında indirim yapılmaktadır",
                    R.drawable.yuzdeellindirim
                )
            )
        }
        binding.cardView3.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    "Dijital kanallardan gerçekleştirilen havalelerde sıfır masraf!",
                    "İnternet ve mobil bankacılıkta aynı hesaplar arasında gerçekleştirilen havale işlemlerinden masraf alınmamaktadır.",
                    R.drawable.dijitalkanalsifirmasraf
                )
            )
        }
        binding.cardView4.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    "10.000 TL ve üzeri harcamanıza, 50 TL Bankkart Lira",
                    "10.000 TL'ye ulaşan kredi kartı harcamanıza 50 tl bankkart Lira verilecektir.",
                    R.drawable.bankkartlira
                )
            )
        }
        binding.cardView6.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    "Vadeli mevduata ek faiz",
                    "İnternet veya mobil bankacılıktan açılan 32-91 gün vadeli TL mevduat hesaplarına +%2 faiz oranı uygulanmaktadır.",
                    R.drawable.vadelimevduataekfaiz
                )
            )
        }
        binding.cardView7.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    "Aidatsız Kredi Kartı",
                    "Süper Şube müşterilerine kart aidatı (ek kartlar dahil) yansıtılmamaktadır",
                    R.drawable.aidatsizkredikarti
                )
            )
        }
        binding.cardView5.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    "Kart işlemlerini sonradan taksitlendirme imkanı",
                    "Kredi kartından yapılan işlemlerde 4 taksite kadar ücretsiz sonradan taksitlendirme yapılabilmektedir.",
                    R.drawable.taksitlendirme
                )
            )
        }
        binding.cardView8.setOnClickListener {
            viewModel.selectKampanya(
                KampanyaModel(
                    "Tüm kamu ATM'leri işlem limitleri dahilinde masrafsız!",
                    "Tüm kamu ATM'lerinde işlem limitleri dahilinde para çekme ve para yatırma işlemlerinden masraf alınmamaktadır.",
                    R.drawable.atmlerlimitdahilimasrafsiz
                )
            )
        }

        // Diğer card view'ları buraya ekleyebilirsiniz
    }

    private fun showCustomDialog(title: String, message: String, imageResId: Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_info_layout)

        val dialogTitle = dialog.findViewById<TextView>(R.id.textViewDialogTitle)
        val dialogText = dialog.findViewById<TextView>(R.id.textViewDialogInfo)
        val closeButton = dialog.findViewById<ImageView>(R.id.closeButton)
        val dialogImageView = dialog.findViewById<ImageView>(R.id.dialogImageView)

        dialogTitle.text = title
        dialogText.text = message
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