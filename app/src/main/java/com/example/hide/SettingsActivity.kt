package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import com.example.hide.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonNotificacion = findViewById<Button>(R.id.buttonNotifications)
        val botonPrivacy = findViewById<Button>(R.id.buttonPrivacy)
        val botonOtros = findViewById<Button>(R.id.buttonOthers)
        val botonLogOut = findViewById<Button>(R.id.buttonLogOut)
        val botonProfile = findViewById<ImageButton>(R.id.bottonRetrocederProfile)
        val auth = FirebaseAuth.getInstance()

        binding.imageView14.setImageResource(R.drawable.profile_png_free_image) // replace 'profile_png_free_image' with your actual image name


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
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        botonProfile.setOnClickListener {
            val intent= Intent(this, ProfileActivity::class.java)
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
                binding.editTextText3b.text = user.nombre
                binding.editTextText4b.text = user.usuario
                if (user.imageBitmap != null){
                    binding.imageView14.setImageBitmap(user.imageBitmap)
                }
            }, { error ->
                // Handle error
            })
        }
    }
}