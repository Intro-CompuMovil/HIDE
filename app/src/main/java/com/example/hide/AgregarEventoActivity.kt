package com.example.hide

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarEventoActivity : AppCompatActivity() {

    private val SELECT_PICTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_evento)


        val botonRetroceder =findViewById<ImageButton>(R.id.bottonRetroceder)
        val AgregarSitio =findViewById<Button>(R.id.buttonAgregarSitio)


        botonRetroceder.setOnClickListener{
            val intent= Intent(this, LugaresActivity::class.java)
            startActivity(intent)
        }

        AgregarSitio.setOnClickListener{
            val intent= Intent(this, LugaresActivity::class.java)
            startActivity(intent)
        }



        // Aplicar insets para el sistema de barras
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración del botón para seleccionar una imagen
        val botonElegirFoto = findViewById<Button>(R.id.botonElegirFoto)
        botonElegirFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, SELECT_PICTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            val selectedImageUri: Uri? = data?.data
            // Aquí puedes usar la URI para mostrar la imagen o hacer algo con ella
        }
    }
}
