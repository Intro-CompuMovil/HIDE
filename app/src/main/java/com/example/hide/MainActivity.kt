package com.example.hide

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.hide.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val botonLogin = findViewById<Button>(R.id.buttonLogin)
        val botonRegister = findViewById<Button>(R.id.buttonRegister)
        val botonAdminLogin = findViewById<Button>(R.id.buttonAdminLogin)

        botonLogin.setOnClickListener{
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            signInUser(email, password)


        }
        botonRegister.setOnClickListener{
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        botonAdminLogin.setOnClickListener{
                val intent= Intent(this, AdminActivity::class.java)
                startActivity(intent)
        }

    }

    private fun signInUser(email: String, password: String){
        if(validateForm() && isEmailValid(email)){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(auth.currentUser)
                    } else {
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Correo o contraseña incorrectos.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true
        val email = binding.editTextEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.editTextEmail.error = "Required."
            valid = false
        } else {
            binding.editTextEmail.error = null
        }
        val password = binding.editTextPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.editTextPassword.error = "Required."
            valid = false
        } else {
            binding.editTextPassword.error = null
        }
        return valid
    }

    private fun isEmailValid(email: String): Boolean {
        if (!email.contains("@") ||
            !email.contains(".") ||
            email.length < 5){
            binding.editTextEmail.error = "Invalid email."
            return false
        }

        return true
    }


    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent= Intent(this, MatchFoundActivity::class.java)
            intent.putExtra("user", currentUser.email)
            startActivity(intent)
        } else {
            binding.editTextEmail.setText("")
            binding.editTextPassword.setText("")
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)

    }
}