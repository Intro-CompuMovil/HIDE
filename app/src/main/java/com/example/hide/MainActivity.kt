package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonLogin = findViewById<Button>(R.id.buttonLogin)
        val botonRegister = findViewById<Button>(R.id.buttonRegister)

        botonLogin.setOnClickListener{
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }
        botonRegister.setOnClickListener{
        val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}