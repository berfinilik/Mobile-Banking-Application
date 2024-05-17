package com.berfinilik.bankingapplication.utils

import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog

class ChangeEmailDialog(private val context:Context,private val onChangeEmail:(String)->Unit) {
    fun showDialog() {
        val editText = EditText(context)
        editText.hint = "Yeni E-posta Adresi"
        val layout = LinearLayout(context)
        layout.setPadding(50, 50, 50, 50)
        layout.addView(editText)

        val alertDialog = AlertDialog.Builder(context)
            .setTitle("E-posta Adresini Değiştir")
            .setView(layout)
            .setPositiveButton("Değiştir") { _, _ ->
                val newEmail = editText.text.toString()
                onChangeEmail(newEmail)
            }
            .setNegativeButton("İptal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }
}