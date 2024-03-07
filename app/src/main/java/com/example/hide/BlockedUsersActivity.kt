package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.example.hide.databinding.ActivityUserBlockedsBinding

class BlockedUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBlockedsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBlockedsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonRegresar= findViewById<ImageButton>(R.id.bottonRetroceder)

        botonRegresar.setOnClickListener {
            val intent= Intent(this, PrivacyActivity::class.java)
            startActivity(intent)
        }

    }
}