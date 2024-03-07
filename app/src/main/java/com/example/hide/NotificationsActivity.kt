package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hide.databinding.ActivityNotifications2Binding

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotifications2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifications2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonGuardar = findViewById<Button>(R.id.buttonSave)
        botonGuardar.setOnClickListener {
            val intent= Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}