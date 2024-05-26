package com.example.hide

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hide.databinding.ActivityEditprofileBinding
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream


class EditProfileActivity : AppCompatActivity() {

    private val userRepository = UserRepository()
    private lateinit var binding: ActivityEditprofileBinding
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CAMERA_PERMISSION = 101
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private var imagen: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.FotodePerfil.setImageResource(R.drawable.profile_png_free_image) // replace 'profile_png_free_image' with your actual image name

        setupClickListeners()
    }

    private fun setupClickListeners() {
        val botonCancelar = findViewById<Button>(R.id.buttonCancel)
        val botonGuardar = findViewById<Button>(R.id.buttonSave)

        botonCancelar.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        botonGuardar.setOnClickListener {
            updateUserProfile()

        }

        binding.FotodePerfil.setOnClickListener {
            requestCameraPermissionAndTakePhoto()
        }
    }

    private fun requestCameraPermissionAndTakePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            dispatchTakePictureIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                // Handle the user denying the camera permission
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            binding.FotodePerfil.setImageBitmap(imageBitmap)
            imagen = imageBitmap!!


        }
    }
    private fun saveImageToFirebase(imageBitmap: Bitmap) {
        val user = Firebase.auth.currentUser
        val profileImageRef = storageRef.child("profileImages/${user?.uid}")

        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = profileImageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            Toast.makeText(this, "Imagen subida con éxito", Toast.LENGTH_SHORT).show()
        }

    }



    private fun updateUserProfile() {
        val userId = userRepository.getCurrentUserId()
        if (userId != null) {
            userRepository.getUserData(userId, { currentUser ->
                // Actualiza solo los campos que han cambiado
                if (binding.editTextText3.text.isNotEmpty()) {
                    currentUser.nombre = binding.editTextText3.text.toString()
                }
                if (binding.editTextText4.text.isNotEmpty()) {
                    currentUser.usuario = binding.editTextText4.text.toString()
                }
                if (binding.editTextText5.text.isNotEmpty()) {
                    currentUser.descripcion = binding.editTextText5.text.toString()
                }
                if (imagen != null) {
                    currentUser.imageBitmap = imagen
                }
                Log.i("mammamia", currentUser.toString())

                // Guarda el usuario actualizado en la base de datos
                userRepository.updateUserData(currentUser, {
                    Toast.makeText(this, "Perfil actualizado con éxito", Toast.LENGTH_SHORT).show()
                     if (imagen != null) {
                      saveImageToFirebase(imagen!!)

                  }
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()  // Cierra la actividad actual
                }, { error ->
                    Toast.makeText(this, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show()
                })
            }, { error ->
                Toast.makeText(this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show()
            })
        }
    }
}


