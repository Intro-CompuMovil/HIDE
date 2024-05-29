package com.example.hide

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.example.hide.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val userRepository = UserRepository()


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonEditPerfil = findViewById<ImageView>(R.id.FotodePerfil1)
        botonEditPerfil.setImageResource(R.drawable.profile_png_free_image)

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

             loadUserData()

    }

    private fun loadUserData() {
        // Load user data
        val uid = userRepository.getCurrentUserId()
        Log.i("mammamia", uid.toString())
        if (uid != null) {
            userRepository.getUserData(uid, { user ->
                // Set TextViews with user data
                binding.editTextText3.text = user.nombre
                binding.editTextText4.text = user.usuario
                if (user.imageBitmap != null){
                    binding.FotodePerfil1.setImageBitmap(user.imageBitmap)
                }
            }, { error ->
                // Handle error
            })
        }
    }

}