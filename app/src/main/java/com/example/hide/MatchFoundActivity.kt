package com.example.hide

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hide.databinding.ActivityMatchFoundBinding
import com.google.android.gms.common.api.Response
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import okhttp3.Route


class MatchFoundActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMatchFoundBinding
    private val REQUEST_CAMERA_PERMISSION = 101
    private val REQUEST_LOCATION_PERMISSION = 102
    private var mGoogleMap:GoogleMap? = null
    private lateinit var countdownTimer: CountDownTimer
    private lateinit var textViewCountdown: TextView
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var launchCamera: ActivityResultLauncher<Intent>
    private lateinit var locationProvider: FusedLocationProviderClient


    private var userLocationMarker: Marker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationProvider = LocationServices.getFusedLocationProviderClient(this)

        val imageContactos = findViewById<ImageView>(R.id.imageContactos)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val botonPerfil = findViewById<ImageView>(R.id.imageViewProfile)
        val botonMatch = findViewById<Button>(R.id.findmatch)

        botonMatch.setOnClickListener {
            val targetLocation = LatLng(4.628528, -74.067283) // Convertido a formato decimal
            drawCircle(targetLocation)

            val intent = Intent(this, FindMatchActivity::class.java)
            startForResult.launch(intent)
        }




        textViewCountdown = findViewById(R.id.countdown_timer)
        val hideOrEyeImageView = findViewById<ImageView>(R.id.HideorEye)

        var isShowingHide = true

        botonPerfil.setOnClickListener{
            val intent= Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.imageContactos.setOnClickListener {
            val intent = Intent(this, AddFriendActivity::class.java)
            startActivity(intent)
        }



        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Aquí inicia el temporizador cuando el usuario regresa
                startCountdown()
            }
        }






        binding.buttonphoto.setOnClickListener {
            requestCamera()
        }
        binding.imageViewProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.imageContactos.setOnClickListener {
            val intent = Intent(this, AddFriendActivity::class.java)
            startActivity(intent)
        }





        launchCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // La foto fue tomada exitosamente, navegar a WinnerActivity
                val intent = Intent(this, WinnerActivity::class.java)
                startActivity(intent)
            }
        }

        hideOrEyeImageView.setOnClickListener {
            isShowingHide = if (isShowingHide) {
                // Cambiar a 'eye', activar el mapa
                hideOrEyeImageView.setImageResource(R.drawable.eye)
                mGoogleMap?.uiSettings?.setAllGesturesEnabled(true)  // Habilitar gestos
                findViewById<FrameLayout>(R.id.map).visibility = View.VISIBLE  // Mostrar el contenedor del mapa
                false
            } else {
                // Cambiar a 'hide', desactivar el mapa
                hideOrEyeImageView.setImageResource(R.drawable.hide)
                mGoogleMap?.uiSettings?.setAllGesturesEnabled(false)  // Deshabilitar gestos
                findViewById<FrameLayout>(R.id.map).visibility = View.GONE  // Ocultar el contenedor del mapa
                true
            }
        }
    }

    private fun requestCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            dispatchTakePictureIntent()
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            enableMyLocation()
        }
    }


    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap?.isMyLocationEnabled = true
            mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = true

            locationProvider.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val userLocation = LatLng(it.latitude, it.longitude)
                    mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))  // Establece el nivel de zoom a 15
                    if (userLocationMarker == null) {
                        userLocationMarker = mGoogleMap?.addMarker(MarkerOptions().position(userLocation).title("Tu ubicación actual"))
                    } else {
                        userLocationMarker?.position = userLocation
                    }
                }
            }.addOnFailureListener {
                Log.d("MapsActivity", "Error al obtener la ubicación actual")
            }
        }
    }







    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(packageManager)?.let {
            launchCamera.launch(takePictureIntent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            } else {
                // Manejar el caso de que el usuario niegue el permiso
            }
        }
    }


    private fun startCountdown() {
        val totalTime = 5 * 60 * 1000
        countdownTimer = object : CountDownTimer(totalTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                textViewCountdown.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                textViewCountdown.text = "00:00"
                val intent = Intent(this@MatchFoundActivity, LoseActivity::class.java)
                startActivity(intent)
            }
        }.start()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        // Habilitar la capa de ubicación si se tienen los permisos necesarios
        enableMyLocation()



    }

    fun drawCircle(location: LatLng) {
        val circleOptions = CircleOptions()
            .center(location)
            .radius(100.0) // Radio en metros
            .strokeWidth(3f)
            .strokeColor(Color.BLUE) // Color del borde del círculo
            .fillColor(0x220000FF) // Color de relleno del círculo con transparencia
        mGoogleMap?.addCircle(circleOptions)
    }








}

