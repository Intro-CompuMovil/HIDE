package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hide.databinding.ActivityPrivacyBinding

class PrivacyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonGuardar = findViewById<Button>(R.id.button7)
        val botonBloqueados = findViewById<Button>(R.id.button4)

        botonGuardar.setOnClickListener {
            val intent= Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        botonBloqueados.setOnClickListener {
            val intent= Intent(this, BlockedUsersActivity::class.java)
            startActivity(intent)
        }

    }
}