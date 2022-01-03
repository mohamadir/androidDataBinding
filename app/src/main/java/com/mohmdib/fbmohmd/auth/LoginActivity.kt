package com.mohmdib.fbmohmd.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.mohmdib.fbmohmd.R
import com.mohmdib.fbmohmd.databinding.ActivityLoginBinding
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {

    lateinit var googleSignInClien: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.loginBtn?.setOnClickListener {
            /*FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(
                    binding.emailEt.text.toString(),
                    binding.passwordEt.text.toString()
                )
                .addOnCompleteListener {


                }*/
            signIn()
        }
        createRequest()

    }

    fun signIn(){
        var intent  = googleSignInClien.signInIntent
        startActivityForResult(intent, 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun createRequest(){
        // Configure Google Sign In
/*        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

         googleSignInClien = GoogleSignIn.getClient(this, gso)*/

    }
}