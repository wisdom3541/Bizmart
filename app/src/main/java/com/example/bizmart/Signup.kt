package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SignupBinding

class Signup : AppCompatActivity() {

    lateinit var binding: SignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBTN.setOnClickListener { nextActivity() }
    }


    private fun nextActivity(){
        val intent = Intent(this, FragmentHolder::class.java)
        startActivity(intent)
    }
}