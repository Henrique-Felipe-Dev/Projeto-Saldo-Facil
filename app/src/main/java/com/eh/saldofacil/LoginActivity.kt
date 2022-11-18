package com.eh.saldofacil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eh.saldofacil.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainIntent = Intent(this, MainActivity::class.java)

        binding.buttonEntrar.setOnClickListener {
            startActivity(mainIntent)
            finish()
        }

    }
}