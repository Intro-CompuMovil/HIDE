package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hide.databinding.ActivityMyFriendsBinding

class MyFriendsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyFriendsBinding
    private lateinit var userRepository: UserRepository
    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = UserRepository() // Initialize userRepository here
        setupCurrentUser()

        binding.bottonRetroceder.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }
        binding.request.setOnClickListener{
            val intent= Intent(this, FriendRequestActivity::class.java)
            startActivity(intent)
        }
        binding.suggestions.setOnClickListener{
            val intent= Intent(this, AddFriendActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupCurrentUser() {
        userRepository.getCurrentUser({ currentUser ->
            this.currentUser = currentUser
            setupFriends()
        }, { error ->
            Toast.makeText(this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupFriends() {
        val friends = currentUser.amigos.filter { it.value == true }
        setupRecyclerView(friends)
    }

    private fun setupRecyclerView(friends: Map<String, Boolean>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFriends)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FriendsAdapter(friends, userRepository)
    }
}