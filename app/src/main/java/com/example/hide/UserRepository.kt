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

    fun getAllUsers(success: (List<User>) -> Unit, failure: (Exception) -> Unit) {
        val usersRef = database.child("usuarios") // Asegúrate de que estás apuntando a la colección correcta en tu base de datos
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = mutableListOf<User>()
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
                        user.uid =
                            userSnapshot.key.toString() // Añade esta línea para guardar el uid en el usuario


                        users.add(user)
                    }
                }
                success(users)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                failure(databaseError.toException())
            }
        })
    }

    fun sendFriendRequest(fromUid: String, toUid: String) {
        val requestRef = database.child("usuarios").child(toUid).child("solicitudesDeAmistad").child(fromUid)
        requestRef.setValue("pendiente")
    }

    fun getCurrentUser(onSuccess: (User) -> Unit, onError: (DatabaseError) -> Unit) {
        val currentUserId = getCurrentUserId()
        if (currentUserId != null) {
            getUserData(currentUserId, onSuccess, onError)
        } else {
            // Maneja el caso en que no hay un usuario actual
        }
    }
    fun acceptFriendRequest(fromUid: String, toUid: String) {
        // Eliminar la solicitud de amistad del usuario que la recibió
        val requestRef = database.child("usuarios").child(toUid).child("solicitudesDeAmistad").child(fromUid)
        requestRef.removeValue()

        // Agregar al usuario que envió la solicitud a la lista de amigos del usuario que la recibió
        val toFriendsRef = database.child("usuarios").child(toUid).child("amigos").child(fromUid)
        toFriendsRef.setValue(true)

        // Agregar al usuario que recibió la solicitud a la lista de amigos del usuario que la envió
        val fromFriendsRef = database.child("usuarios").child(fromUid).child("amigos").child(toUid)
        fromFriendsRef.setValue(true)
    }

    fun rejectFriendRequest(fromUid: String, toUid: String) {
        // Eliminar la solicitud de amistad del usuario que la recibió
        val requestRef = database.child("usuarios").child(toUid).child("solicitudesDeAmistad").child(fromUid)
        requestRef.removeValue()
    }

    fun getUserByUid(uid: String, onSuccess: (User) -> Unit, onError: (DatabaseError) -> Unit) {
        val userRef = database.child("usuarios").child(uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    user.uid = dataSnapshot.key.toString()

                    val imageRef = storage.getReference("profileImages/${user!!.uid}")
                    val ONE_MEGABYTE: Long = 1024 * 1024

                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        user.imageBitmap = bitmap
                        Log.i("mammamia3", user.toString())
                        onSuccess(user!!)
                    }.addOnFailureListener { exception ->
                        // Si la descarga falla, maneja el error
                        Log.e("Firebase", "Error al descargar la imagen", exception)
                        Log.i("mammamia3", user.toString())
                        onSuccess(user!!)
                    }
                  //  onSuccess(user)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onError(databaseError)
            }
        })
    }

    fun removeFriend(friendUid: String) {
        val currentUserUid = getCurrentUserId()!!// Obtén el UID del usuario actual aquí

        // Elimina al amigo de la lista de amigos del usuario actual
        val currentUserFriendsRef = database.child("usuarios").child(currentUserUid).child("amigos").child(friendUid)
        currentUserFriendsRef.removeValue()

        // Elimina al usuario actual de la lista de amigos del amigo
        val friendFriendsRef = database.child("usuarios").child(friendUid).child("amigos").child(currentUserUid)
        friendFriendsRef.removeValue()
    }
}