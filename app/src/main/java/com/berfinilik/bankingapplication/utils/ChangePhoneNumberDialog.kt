package com.berfinilik.bankingapplication.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout

class ChangePhoneNumberDialog(private val context: Context, private val onChangePhoneNumber: (String) -> Unit) {

    fun showDialog() {
        val editText = EditText(context)
        editText.hint = "Yeni Telefon Numarası"
        val layout = LinearLayout(context)
        layout.setPadding(50, 50, 50, 50)
        layout.addView(editText)

        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Telefon Numarasını Değiştir")
            .setView(layout)
            .setPositiveButton("Değiştir") { _, _ ->
                val newPhoneNumber = editText.text.toString()
                onChangePhoneNumber(newPhoneNumber)
            }
            .setNegativeButton("İptal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }
}