package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.hide.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonEditPerfil = findViewById<ImageView>(R.id.imageView15)
        val botonConfig = findViewById<ImageView>(R.id.config) //falta que juanjo configure la pantalla
        val botonRegreso = findViewById<ImageView>(R.id.imageView9)

        botonRegreso.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }

        botonEditPerfil.setOnClickListener{
            val intent= Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        botonConfig.setOnClickListener{
            val intent= Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }

}