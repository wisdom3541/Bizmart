package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SplashScreenBinding


class SplashScreen : AppCompatActivity() {

    private lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getStarted.setOnClickListener { nextactivity() }
    }

    private fun nextactivity(){
        val intent = Intent(this, SignupSignin::class.java)
        startActivity(intent)
    }

}