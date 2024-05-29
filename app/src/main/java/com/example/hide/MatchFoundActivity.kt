package com.example.hide

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.CountDownTimer
import android.os.StrictMode
import android.provider.MediaStore
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import okhttp3.Route
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import org.osmdroid.bonuspack.routing.MapQuestRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.util.GeoPoint

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
    val userRepository = UserRepository()


    private var userLocationMarker: Marker? = null


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationProvider = LocationServices.getFusedLocationProviderClient(this)

        val imageContactos = findViewById<ImageView>(R.id.imageContactos)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val botonPerfil = findViewById<ImageView>(R.id.imageViewProfile)
        val botonMatch = findViewById<Button>(R.id.findmatch)

        binding.textViewinformacion.visibility = View.GONE
        binding.textViewaviso.visibility = View.GONE
        binding.buttonphoto.visibility = View.GONE

        botonMatch.setOnClickListener {
            val intent = Intent(this, FindMatchActivity::class.java)
            startForResult.launch(intent)


         }

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Aquí inicia el temporizador cuando el usuario regresa
                if (requestLocationPermission()){
                    startCountdown()
                    binding.findmatch.visibility = View.GONE
                    binding.textViewinformacion.visibility = View.VISIBLE
                    binding.textViewaviso.visibility = View.VISIBLE
                    binding.buttonphoto.visibility = View.VISIBLE
                    locationProvider.lastLocation.addOnSuccessListener { location: Location? ->
                        location?.let {
                            val userLocation = LatLng(it.latitude, it.longitude)
                            val DISTANCE = 0.0009 // valor quemado de lo que son aproximadamente 50 metros
                            val PERSON_POSITION = LatLng(userLocation.latitude-DISTANCE,  userLocation.longitude)
                            val quemado = LatLng( 4.62714,  -74.06258)
                            val targetLocation = calculateMidPoint(userLocation, PERSON_POSITION)

                            drawCircle(targetLocation)
                            drawRouteFromCurrentLocationToCircleCenter(userLocation, targetLocation)
                        }
                    }}
            }
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
                mGoogleMap?.uiSettings?.setAllGesturesEnabled(true)
                findViewById<FrameLayout>(R.id.map).visibility = View.VISIBLE

                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    userRepository.updateUserStatus(user.uid, "activo", {
                        // Maneja el éxito
                    }, { error ->
                        // Maneja el error
                    })
                }

                false

            } else {
                // Cambiar a 'hide', desactivar el mapa
                hideOrEyeImageView.setImageResource(R.drawable.hide)
                mGoogleMap?.uiSettings?.setAllGesturesEnabled(false)
                findViewById<FrameLayout>(R.id.map).visibility = View.GONE

                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    userRepository.updateUserStatus(user.uid, "inactivo", {
                        // Maneja el éxito
                    }, { error ->
                        // Maneja el error
                    })
                }

                true
            }
        }

        requestLocationPermission()
    }

    private fun requestCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            dispatchTakePictureIntent()
        }
    }

    private fun requestLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        return false
        } else {
            enableMyLocation()
            return true
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
                // Si el permiso fue denegado, muestra un diálogo explicando por qué necesitas el permiso
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder(this)
                        .setTitle("Permiso de ubicación necesario")
                        .setMessage("Esta aplicación necesita el permiso de ubicación para mostrar tu ubicación en el mapa.")
                        .setPositiveButton("Aceptar") { _, _ ->
                            // Solicita el permiso de nuevo
                            requestLocationPermission()
                        }
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show()
                } else {
                    // El usuario ha marcado la opción "No preguntar de nuevo", no puedes solicitar el permiso de nuevo
                    Toast.makeText(this, "Permiso de ubicación necesario para mostrar tu ubicación en el mapa.", Toast.LENGTH_SHORT).show()
                }
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

        fun calculateMidPoint(location1: LatLng, location2: LatLng): LatLng {
            val midLatitude = (location1.latitude + location2.latitude) / 2
            val midLongitude = (location1.longitude + location2.longitude) / 2
            return LatLng(midLatitude, midLongitude)
        }

    fun drawRouteFromCurrentLocationToCircleCenter(currentLocation: LatLng, circleCenter: LatLng) {
        // Crea un RoadManager
        val roadManager: RoadManager = OSRMRoadManager(this, "ANDROID")
      //  roadManager.addRequestOption("vehicle=car") // Establece el tipo de ruta a "caminar"

        // Define las coordenadas de inicio y fin de la ruta
        val start = GeoPoint(currentLocation.latitude, currentLocation.longitude)
        val end = GeoPoint(circleCenter.latitude, circleCenter.longitude)

        // Solicita la ruta a OpenStreetMap
        val waypoints = ArrayList<GeoPoint>()
        waypoints.add(start)
        waypoints.add(end)
        val road = roadManager.getRoad(waypoints)

        // Convierte la ruta a una lista de LatLng
        val latLngRoute = road.mRouteHigh.map { LatLng(it.latitude, it.longitude) }

        // Dibuja la ruta en el mapa de Google Maps
        val polylineOptions = PolylineOptions().addAll(latLngRoute).color(Color.RED).width(5f)
        mGoogleMap!!.addPolyline(polylineOptions)
    }



}

