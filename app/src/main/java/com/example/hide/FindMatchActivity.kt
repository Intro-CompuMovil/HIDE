package com.example.hide

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FindMatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_find_match)
        val botonAceptar =findViewById<Button>(R.id.Aceptar)
        val botonRechazar =findViewById<Button>(R.id.Rechazar)

        botonAceptar.setOnClickListener{
            setResult(Activity.RESULT_OK)
            finish()
        }

        botonRechazar.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }
}