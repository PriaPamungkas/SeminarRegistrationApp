package com.example.seminarregistrationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val name = intent.getStringExtra("EXTRA_NAME")
        val email = intent.getStringExtra("EXTRA_EMAIL")
        val phone = intent.getStringExtra("EXTRA_PHONE")
        val gender = intent.getStringExtra("EXTRA_GENDER")
        val seminar = intent.getStringExtra("EXTRA_SEMINAR")

        findViewById<TextView>(R.id.tvResultName).text = "Nama: $name"
        findViewById<TextView>(R.id.tvResultEmail).text = "Email: $email"
        findViewById<TextView>(R.id.tvResultPhone).text = "No HP: $phone"
        findViewById<TextView>(R.id.tvResultGender).text = "Jenis Kelamin: $gender"
        findViewById<TextView>(R.id.tvResultSeminar).text = "Seminar: $seminar"

        findViewById<Button>(R.id.btnBackHome).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
