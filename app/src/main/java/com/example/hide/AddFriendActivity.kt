package com.example.hide

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hide.databinding.ActivityAddfriendBinding
import com.example.hide.databinding.ActivityMyFriendsBinding

class AddFriendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddfriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddfriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
