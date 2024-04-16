package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hide.databinding.ActivityAddfriendBinding

class AddFriendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddfriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addfriend)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonRegreso = findViewById<ImageButton>(R.id.bottonRetroceder)
        val botonMisAmigos = findViewById<Button>(R.id.friends)
        val botonSolicitud = findViewById<Button>(R.id.request)
        val botonAdd = findViewById<EditText>(R.id.addorremove)
        val botonDelete =findViewById<ImageButton>(R.id.deletedrecommended)
        val Amigo  =findViewById<ImageView>(R.id.imageView37)

        botonRegreso.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }

        Amigo.setOnClickListener {
            val intent= Intent(this, FriendProfileActivity::class.java)
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

        botonAdd.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }

        botonDelete.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }

    }
}