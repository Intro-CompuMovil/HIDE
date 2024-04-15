package com.example.hide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LugaresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lugares)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val BotonRetroceder = findViewById<ImageButton>(R.id.bottonRetroceder)
        val BotonAgregarSitio =findViewById<Button>(R.id.AgregarLugar)

        BotonRetroceder.setOnClickListener{
            val intent= Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

        BotonAgregarSitio.setOnClickListener{
                val intent= Intent(this, AgregarEventoActivity::class.java)
                startActivity(intent)
            }

    }
}