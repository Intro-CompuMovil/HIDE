package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoseActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository
    private var currentUserUid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lose)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userRepository = UserRepository()
        currentUserUid = userRepository.getCurrentUserId()

        val botonGoBack = findViewById<Button>(R.id.GoBack)


        botonGoBack.setOnClickListener{

            currentUserUid?.let { uid ->
                userRepository.getUserByUid(uid, { user ->
                    user.oponente?.let { opponentUid ->
                        userRepository.removeOpponent(uid)
                        userRepository.removeOpponent(opponentUid)
                        userRepository.deleteChallengePhoto(currentUserUid!!, {
                            val intent = Intent(this, MatchFoundActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, { error ->
                            // Handle error here
                        })

                    }
                }, { error ->
                    userRepository.deleteChallengePhoto(currentUserUid!!, {
                        val intent = Intent(this, MatchFoundActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, { error ->
                        // Handle error here
                    })
                })

                userRepository.deleteChallengePhoto(currentUserUid!!, {
                    val intent = Intent(this, MatchFoundActivity::class.java)
                    startActivity(intent)
                    finish()
                }, { error ->
                    // Handle error here
                })
            }
            val intent = Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}