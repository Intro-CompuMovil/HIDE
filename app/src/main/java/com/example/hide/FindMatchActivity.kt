package com.example.hide

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FindMatchActivity : AppCompatActivity() {
    private lateinit var userRepository: UserRepository
    private lateinit var currentUserUid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_find_match)

        userRepository = UserRepository()
        currentUserUid = userRepository.getCurrentUserId()!!

        val botonAceptar =findViewById<Button>(R.id.Aceptar)
        val botonRechazar =findViewById<Button>(R.id.Rechazar)





        userRepository.getUserByUid(currentUserUid, { user ->
            if (user.solicitudesDeDesafio.isNotEmpty()) {
                // The user has a challenge request
                val challengerUid = user.solicitudesDeDesafio.keys.first()

                findViewById<View>(R.id.challengeView).visibility = View.VISIBLE
                findViewById<RecyclerView>(R.id.friendsList).visibility = View.GONE

                botonAceptar.setOnClickListener{
                    userRepository.setOpponent(currentUserUid, challengerUid)
                    setResult(Activity.RESULT_OK)
                    finish()
                }

                botonRechazar.setOnClickListener{
                    userRepository.removeChallenge(currentUserUid, challengerUid)
                    setResult(Activity.RESULT_CANCELED)
                    finish()

                }

                userRepository.getUserByUid(challengerUid, { challenger ->
                    // Display the challenger's information
                    findViewById<TextView>(R.id.textView3).text = "el @${challenger.usuario} te desafia a un duelo"
                    // Set the challenger's photo to imageView14
                }, { error ->
                    // Handle error here
                }

                )
            } else {
                // The user doesn't have a challenge request
                // Hide the challenge view and show the friends list
                findViewById<View>(R.id.challengeView).visibility = View.GONE
                findViewById<RecyclerView>(R.id.friendsList).visibility = View.VISIBLE

                // Set up the friends list
                setupFriends()

            }
        }, { error ->
            // Handle error here
        })
    }

    private fun setupFriends() {
        userRepository.getUserByUid(currentUserUid, { user ->
            val friends = user.amigos.filter { it.value == true }
            setupRecyclerView(friends)
        }, { error ->
            // Handle error here
        })
    }

    private fun setupRecyclerView(friends: Map<String, Boolean>) {
        val recyclerView = findViewById<RecyclerView>(R.id.friendsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DesafiarAdapter(friends, userRepository) { friendUid ->
            userRepository.sendChallengeRequest(friendUid)
            setResult(20)
            finish()
        }
    }
}