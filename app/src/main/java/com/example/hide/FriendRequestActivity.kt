package com.example.hide

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hide.databinding.ActivityAddfriendBinding
import com.example.hide.databinding.ActivityFriendRequestBinding

class FriendRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFriendRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}