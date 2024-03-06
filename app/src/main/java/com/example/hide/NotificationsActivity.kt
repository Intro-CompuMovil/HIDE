package com.example.hide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hide.databinding.ActivityNotifications2Binding

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotifications2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifications2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}