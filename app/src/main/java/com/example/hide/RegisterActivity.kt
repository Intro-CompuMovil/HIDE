package com.example.hide

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.hide.databinding.ActivityRegisterBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private var imagen: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonSignUp = findViewById<Button>(R.id.buttonSingUP)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        botonSignUp.setOnClickListener {
            val email = binding.editTextEmailAddressregister.text.toString()
            val password = findViewById<EditText>(R.id.editTextPickPasswordregister).text.toString()
            val nombre = findViewById<EditText>(R.id.editTextCompleteName).text.toString()
            val usuario = findViewById<EditText>(R.id.editTextUserName).text.toString()

            if (validarCampos(email, password, nombre, usuario )) {

                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Solicitar permiso de ubicación si no se ha concedido
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
                }else{
                    registrarUsuario(email, password, nombre, usuario)

                    val intent= Intent(this, MatchFoundActivity::class.java)
                    startActivity(intent)
                }

            } else {
                Log.i("RegisterActivity", "Campos no válidos")
                Toast.makeText(this, "Por favor, completa todos los campos correctamente.", Toast.LENGTH_SHORT).show()
            }


        }

    }

    private fun validarCampos(email: String, password: String, nombre: String, usuario:  String): Boolean {
      //  return email.isNotEmpty() && password.isNotEmpty() && nombre.isNotEmpty() && usuario.isNotEmpty()
          return isEmailandPasswordValid(email, password) && nombre.isNotEmpty() && usuario.isNotEmpty()
    }

    private fun isEmailandPasswordValid(email: String, password: String): Boolean {
       var valid = true
        if (!email.contains("@") ||
            !email.contains(".") ||
            email.length < 5){
            binding.editTextEmailAddressregister.error = "Invalid email."
            valid = false
        }
        var letterCount = 0
        var numberCount = 0

        for (char in password) {
            if (char.isLetter()) {
                letterCount++
            } else if (char.isDigit()) {
                numberCount++
            }
        }

        if (letterCount < 7 || numberCount < 1) {
            binding.editTextPickPasswordregister.error = "La contraseña debe tener al menos 7 letras y 1 número."
            valid = false
        } else if (password != binding.editTextConfirmPassword.text.toString()){
            binding.editTextConfirmPassword.error = "Las contraseñas no coinciden."
            valid = false
        }


        return valid
    }

    @SuppressLint("MissingPermission")
    private fun registrarUsuario(email: String, password: String, nombre: String, usuario: String) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val db = FirebaseDatabase.getInstance()

                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    val userData = hashMapOf(
                        "nombre" to nombre,
                        "usuario" to usuario,
                        "latitud" to location?.latitude.toString(),
                        "longitud" to location?.longitude.toString(),
                        "estado" to "activo",
                        "descripcion" to null,
                        "victorias" to 0,
                        "derrotas" to 0,
                        "oponente" to null,
                    )
                    db.reference.child("usuarios").child(user!!.uid).setValue(userData)
                        .addOnSuccessListener {
                            Toast.makeText(baseContext, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(baseContext, "Error al registrar el usuario.", Toast.LENGTH_SHORT).show()
                        }
                }

            } else {
                Toast.makeText(baseContext, "Error al registrar el usuario.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}