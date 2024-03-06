package com.example.hide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hide.databinding.ActivityEditprofileBinding
import com.example.hide.databinding.ActivityMatchFoundBinding

class MatchFoundActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchFoundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}