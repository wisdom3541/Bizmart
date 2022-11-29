package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SignupSigninBinding

class SignupSignin : AppCompatActivity() {

    lateinit var binding: SignupSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignupSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBTN.setOnClickListener {  nextactivity() }
    }

    fun nextactivity(){
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }
}