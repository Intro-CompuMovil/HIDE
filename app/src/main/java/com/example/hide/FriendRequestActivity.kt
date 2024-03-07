package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hide.databinding.ActivityAddfriendBinding
import com.example.hide.databinding.ActivityFriendRequestBinding

class FriendRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFriendRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonRegreso = findViewById<ImageButton>(R.id.bottonRetroceder)
        val botonMisAmigos = findViewById<Button>(R.id.friends)
        val botonSugerencias = findViewById<Button>(R.id.suggestions)

        botonRegreso.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }

        botonMisAmigos.setOnClickListener{
            val intent= Intent(this, MyFriendsActivity::class.java)
            startActivity(intent)
        }


        botonSugerencias.setOnClickListener{
            val intent= Intent(this, AddFriendActivity::class.java)
            startActivity(intent)
        }
    }
}