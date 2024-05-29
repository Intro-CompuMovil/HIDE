package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FriendProfileActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository
    private lateinit var userUid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_friend_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fotoPerfil = findViewById<ImageView>(R.id.FotodePerfil5)
        fotoPerfil.setImageResource(R.drawable.profile_png_free_image)


        userRepository = UserRepository()
        userUid = intent.getStringExtra("USER_UID")!!

        setupUserProfile()

        val botonRetroceder = findViewById<ImageButton>(R.id.bottonRetroceder)

        botonRetroceder.setOnClickListener{
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUserProfile() {
        userRepository.getUserByUid(userUid, { user ->
            // Aquí puedes configurar los campos de texto y la imagen en tu vista para mostrar la información del usuario
            findViewById<TextView>(R.id.recommend_friends2).text =  user.usuario
            findViewById<TextView>(R.id.textViewEstado).text = "Estado: " + user.estado
            findViewById<TextView>(R.id.textViewVictorias).text ="Victorias: " +  user.victorias.toString()
            findViewById<TextView>(R.id.textViewDerrotas).text = "Derrotas: " + user.derrotas.toString()

            if(user.descripcion != null)
                findViewById<TextView>(R.id.textViewDescripcion).text = "Descripcion: " + user.descripcion


            Log.i("mammamiafoto", user.imageBitmap.toString())
            if (user.imageBitmap != null){
                findViewById<ImageView>(R.id.FotodePerfil5).setImageBitmap(user.imageBitmap)
            }
            // ...
        }, { error ->
            Toast.makeText(this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show()
        })
    }
}