package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hide.databinding.ActivityAddfriendBinding
import com.example.hide.databinding.ActivityFriendRequestBinding

class FriendRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendRequestBinding
    private lateinit var userRepository: UserRepository
    private lateinit var currentUser: User
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFriendRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonRegreso = findViewById<ImageButton>(R.id.bottonRetroceder)
        val botonMisAmigos = findViewById<Button>(R.id.friends)
        val botonSugerencias = findViewById<Button>(R.id.suggestions)

        userRepository = UserRepository() // Initialize userRepository here
        setupCurrentUser()

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

    private fun setupFriendRequests() {
        val friendRequests = currentUser.solicitudesDeAmistad.filter { it.value == "pendiente" }
        setupRecyclerView(friendRequests)
    }

    private fun setupRecyclerView(friendRequests: Map<String, String>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRequest)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FriendRequestAdapter(friendRequests, userRepository, { friendRequestUid -> acceptFriendRequest(friendRequestUid) }, { friendRequestUid -> rejectFriendRequest(friendRequestUid) })    }

    private fun acceptFriendRequest(friendRequestUid: String) {
        userRepository.acceptFriendRequest(friendRequestUid, currentUser.uid)
    }

    private fun rejectFriendRequest(friendRequestUid: String) {
        userRepository.rejectFriendRequest(friendRequestUid, currentUser.uid)
    }

    private fun setupCurrentUser() {
        userRepository.getCurrentUser({ currentUser ->
            this.currentUser = currentUser
            setupFriendRequests()
        }, { error ->
            Toast.makeText(this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show()
        })
    }
}