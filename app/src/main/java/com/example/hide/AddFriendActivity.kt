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
import com.example.hide.databinding.ActivityMyFriendsBinding

class AddFriendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddfriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddfriendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonRegreso = findViewById<ImageButton>(R.id.imageButtonRegreso)
        val botonMisAmigos = findViewById<Button>(R.id.friends)
        val botonSolicitud = findViewById<Button>(R.id.request)

        botonRegreso.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }

        botonMisAmigos.setOnClickListener{
            val intent= Intent(this, MyFriendsActivity::class.java)
            startActivity(intent)
        }


        botonSolicitud.setOnClickListener{
            val intent= Intent(this, FriendRequestActivity::class.java)
            startActivity(intent)
        }

    }
}
