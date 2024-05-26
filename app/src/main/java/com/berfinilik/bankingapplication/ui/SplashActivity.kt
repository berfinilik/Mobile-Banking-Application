package com.berfinilik.bankingapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.berfinilik.bankingapplication.MainActivity
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.ui.sign.SignInFragment

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Tema ayarla
        setTheme(R.style.Theme_BankingApplication_Splash)
        setContentView(R.layout.activity_splash)

        // Gecikme süresi (3 saniye)
        Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}