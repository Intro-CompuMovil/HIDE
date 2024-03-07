package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hide.databinding.ActivityAddfriendBinding
import com.example.hide.databinding.ActivityEditprofileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditprofileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonCancelar = findViewById<Button>(R.id.buttonCancel)
        val botonGuardar = findViewById<Button>(R.id.buttonSave)

        botonCancelar.setOnClickListener {
            val intent= Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        botonGuardar.setOnClickListener {
            val intent= Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }
}