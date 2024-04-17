package com.example.hide

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hide.databinding.ActivityMatchFoundBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


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
        botonMatch.setOnClickListener {
            val intent = Intent(this, FindMatchActivity::class.java)
            startForResult.launch(intent)
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
                // Si actualmente muestra 'hide', cambia a 'eye'
                hideOrEyeImageView.setImageResource(R.drawable.eye)
                false
            } else {
                // Si actualmente muestra 'eye', cambia a 'hide'
                hideOrEyeImageView.setImageResource(R.drawable.hide)
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

            // Solicitar la última ubicación conocida del proveedor de ubicación
            locationProvider.lastLocation.addOnSuccessListener { location: Location? ->
                // Verifica si la ubicación no es nula y actualiza la cámara del mapa
                location?.let {
                    val userLocation = LatLng(it.latitude, it.longitude)
                    mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15.0f))
                }
            }.addOnFailureListener {
                // Manejar la situación donde la obtención de la ubicación falla
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

        val bogota = LatLng(4.7110, -74.0721)

        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 12.0f))

        // Opcional: Agregar un marcador en Bogotá
        mGoogleMap?.addMarker(MarkerOptions().position(bogota).title("Marcador en Bogotá"))

        // Habilitar la capa de ubicación si se tienen los permisos necesarios
        enableMyLocation()

    }
}

