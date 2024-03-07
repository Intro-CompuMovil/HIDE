package com.example.hide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.hide.databinding.ActivityOthersBinding

class OthersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOthersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOthersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonRegresar= findViewById<ImageView>(R.id.imageView34)
        val botonEliminar= findViewById<Button>(R.id.button8)

        botonRegresar.setOnClickListener {
            val intent= Intent(this, PrivacyActivity::class.java)
            startActivity(intent)
        }
        botonEliminar.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}