package com.example.hide

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.hide.databinding.ActivityMyFriendsBinding

class MyFriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}