package com.berfinilik.bankingapplication.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout

class ChangePasswordDialog(private val context: Context, private val onChangePassword: (String) -> Unit) {

    fun showDialog() {
        val editText = EditText(context)
        editText.hint = "Yeni Şifre"
        val layout = LinearLayout(context)
        layout.setPadding(50, 50, 50, 50)
        layout.addView(editText)

        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Şifreyi Değiştir")
            .setView(layout)
            .setPositiveButton("Değiştir") { _, _ ->
                val newPassword = editText.text.toString()
                onChangePassword(newPassword)
            }
            .setNegativeButton("İptal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }
}