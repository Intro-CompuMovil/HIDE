package com.example.hide

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class UserRepository {
    private val database = FirebaseDatabase.getInstance().getReference()
    private val auth = FirebaseAuth.getInstance()
    val storage = FirebaseStorage.getInstance()
    fun getUserData(uid: String, onSuccess: (User) -> Unit, onError: (DatabaseError) -> Unit) {
        val userRef = database.child("usuarios").child(uid)
        userRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                user!!.uid = uid
                val imageRef = storage.getReference("profileImages/${user!!.uid}")
                val ONE_MEGABYTE: Long = 1024 * 1024

                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        user.imageBitmap = bitmap
                        Log.i("mammamia2", user.toString())
                        onSuccess(user!!)
                    }.addOnFailureListener { exception ->
                        // Si la descarga falla, maneja el error
                        Log.e("Firebase", "Error al descargar la imagen", exception)
                        Log.i("mammamia2", user.toString())
                        onSuccess(user!!)
                    }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onError(databaseError)
            }
        })
    }

    fun updateUserData(user: User, onSuccess: () -> Unit, onError: (DatabaseError) -> Unit) {
        val userRef = database.child("usuarios").child(user.uid)

        // Crea un mapa con los campos que quieres actualizar
        val userUpdates = hashMapOf<String, Any?>(
            "nombre" to user.nombre,
            "usuario" to user.usuario,
            "descripcion" to user.descripcion
            // No incluyas el campo que quieres ignorar
        )

        userRef.updateChildren(userUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception as DatabaseError)
            }
        }
    }

    fun updateUserStatus(uid: String, status: String, onSuccess: () -> Unit, onError: (DatabaseError) -> Unit) {
        val userRef = database.child("usuarios").child(uid)

        // Crea un mapa con los campos que quieres actualizar
        val userUpdates = hashMapOf<String, Any?>(
            "estado" to status
        )

        userRef.updateChildren(userUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception as DatabaseError)
            }
        }
    }
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}