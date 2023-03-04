package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SignupSigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupSignin : AppCompatActivity() {

    lateinit var binding: SignupSigninBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignupSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signINBTN.setOnClickListener { signIn() }

        binding.signUpBTN.setOnClickListener { signUp() }


    }

    private fun signIn() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
    }

    private fun signUp() {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }

}


