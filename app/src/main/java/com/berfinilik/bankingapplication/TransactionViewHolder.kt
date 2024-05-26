package com.berfinilik.bankingapplication

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.berfinilik.bankingapplication.Domain.Transaction
import com.berfinilik.bankingapplication.databinding.TransactionItemLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class TransactionViewHolder(private val binding: TransactionItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(transaction: Transaction) {
        binding.apply {
            transactionTypeTextView.text = transaction.transactionType
            amountTextView.text = transaction.amount.toString()
            dateTextView.text = transaction.date

            Glide.with(itemView.context)
                .load(transaction.picture)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("Glide", "Görüntü yüklenirken hata oluştu: $e")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?, // Jenerik tip kaldırıldı
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(transactionIcon)
        }
    }
}