package com.example.hide

import android.graphics.Bitmap

data class User(
    var uid: String = "",
    var nombre: String? = null,
    var usuario: String? = null,
    var victorias: Int = 0,
    var derrotas: Int = 0,
    var latitud: String? = null,
    var longitud: String? = null,
    var estado: String? = null,
    var descripcion: String? = null,
    var oponente: String? = null,
    var imageBitmap: Bitmap? = null ,
    var fcmToken: String? = null,
    val amigos: MutableMap<String, Boolean> = mutableMapOf(),
    val solicitudesDeAmistad: MutableMap<String, String> = mutableMapOf()


)
