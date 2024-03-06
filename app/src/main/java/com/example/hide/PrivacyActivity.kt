package com.example.hide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hide.databinding.ActivityPrivacyBinding

class PrivacyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}