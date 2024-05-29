package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository
    private var currentUserUid: String? = null
    private var opponentUid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirmation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        userRepository = UserRepository()
        currentUserUid = userRepository.getCurrentUserId()

        val challengePhotoImageView = findViewById<ImageView>(R.id.imageViewChallenge)
        val acceptButton = findViewById<Button>(R.id.AceptarC)
        val declineButton = findViewById<Button>(R.id.RechazarC)

        userRepository.getUserByUid(currentUserUid!!, { user ->
            val photoUrl = user.fotoDesafio
            opponentUid= user.oponente
            if (photoUrl != null) {
                Picasso.get().load(photoUrl).into(challengePhotoImageView)
            }
        }, { error ->
            // Handle error here
        })

        acceptButton.setOnClickListener {
            userRepository.setGameStatus(currentUserUid!!, opponentUid!!)
            val intent = Intent(this, LoseActivity::class.java)
            startActivity(intent)
        }
        declineButton.setOnClickListener {
            userRepository.deleteChallengePhoto(currentUserUid!!, {
                val intent = Intent(this, MatchFoundActivity::class.java)
                startActivity(intent)
            }, { error ->
                // Handle error here
            })
        }
    }
}