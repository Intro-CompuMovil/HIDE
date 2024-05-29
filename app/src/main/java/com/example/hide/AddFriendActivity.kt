package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hide.databinding.ActivityAddfriendBinding

class AddFriendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddfriendBinding
    private lateinit var userRepository: UserRepository
    private lateinit var currentUser: User
    private lateinit var allUsers: List<User>
    private lateinit var potentialFriends: List<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addfriend)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userRepository = UserRepository() // Initialize userRepository here

        setupCurrentUser()

        val botonRegreso = findViewById<ImageButton>(R.id.bottonRetroceder)
        val botonMisAmigos = findViewById<Button>(R.id.friends)
        val botonSolicitud = findViewById<Button>(R.id.request)
        val botonAdd = findViewById<EditText>(R.id.addorremove)


        botonRegreso.setOnClickListener {
            val intent= Intent(this, MatchFoundActivity::class.java)
            startActivity(intent)
        }

   /*     Amigo.setOnClickListener {
            val intent= Intent(this, FriendProfileActivity::class.java)
            startActivity(intent)
        }*/


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



    }


    private fun setupCurrentUser() {
        userRepository.getCurrentUser({ currentUser ->
            this.currentUser = currentUser
            setupAllUsers()
        }, { error ->
            Toast.makeText(this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupAllUsers() {
        userRepository.getAllUsers({ allUsers ->
            this.allUsers = allUsers
            setupPotentialFriends()
            setupRecyclerView()
        }, { error ->
            Toast.makeText(this, "Error al obtener la lista de usuarios", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupPotentialFriends() {
        potentialFriends = allUsers.filter { user ->
            Log.i("mammamiatest", user.toString())
            user.uid != currentUser.uid &&
                    !currentUser.amigos.containsKey(user.uid) &&
                    !currentUser.solicitudesDeAmistad.containsKey(user.uid)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserAdapter(potentialFriends, { user -> sendFriendRequest(user) })
    }

    private fun sendFriendRequest(user: User) {
        userRepository.sendFriendRequest(currentUser.uid, user.uid)
    }
}
