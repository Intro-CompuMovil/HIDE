package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.hide.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonNotificacion = findViewById<Button>(R.id.buttonNotifications)
        val botonPrivacy = findViewById<Button>(R.id.buttonPrivacy)
        val botonOtros = findViewById<Button>(R.id.buttonOthers)
        val botonLogOut = findViewById<Button>(R.id.buttonLogOut)
        val botonProfile = findViewById<ImageButton>(R.id.bottonRetrocederProfile)

        botonNotificacion.setOnClickListener {
            val intent= Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }
        botonPrivacy.setOnClickListener {
            val intent= Intent(this, PrivacyActivity::class.java)
            startActivity(intent)
        }
        botonOtros.setOnClickListener {
            val intent= Intent(this, OthersActivity::class.java)
            startActivity(intent)
        }
        botonLogOut.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        botonProfile.setOnClickListener {
            val intent= Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }
}