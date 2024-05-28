package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.hide.databinding.ActivityMyFriendsBinding

class MyFriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonRegreso = findViewById<ImageButton>(R.id.bottonRetroceder)
        val botonSugerencias = findViewById<Button>(R.id.suggestions)
        val botonSolicitud = findViewById<Button>(R.id.request)

        botonRegreso.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }

        botonSugerencias.setOnClickListener{
            val intent= Intent(this, AddFriendActivity::class.java)
            startActivity(intent)
        }


        botonSolicitud.setOnClickListener{
            val intent= Intent(this, FriendRequestActivity::class.java)
            startActivity(intent)
        }

    }
}