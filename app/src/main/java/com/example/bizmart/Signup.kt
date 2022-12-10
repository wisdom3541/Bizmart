package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SignupBinding
import com.google.type.FractionOrBuilder

class Signup : AppCompatActivity() {

    lateinit var binding: SignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUp.setOnClickListener { signUp() }

        binding.altLogin.setOnClickListener{ signIn() }
    }


    private fun signIn() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
    }

    private fun signUp() {
        val intent = Intent(this, FragmentHolder::class.java)
        startActivity(intent)
    }
}