package com.example.hide

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.example.hide.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonEditPerfil = findViewById<ImageView>(R.id.FotodePerfil)
        val botonConfig = findViewById<Button>(R.id.buttonSetting)
        val botonRegreso = findViewById<ImageButton>(R.id.bottonRetroceder)

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