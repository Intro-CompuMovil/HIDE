package com.example.hide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hide.databinding.ActivityUserBlockedsBinding

class BlockedUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBlockedsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBlockedsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}