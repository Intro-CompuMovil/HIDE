package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hide.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonSignUp = findViewById<Button>(R.id.buttonSingUP)

        botonSignUp.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }

    }
}