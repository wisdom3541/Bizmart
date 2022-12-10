package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SigninBinding

class SignIn : AppCompatActivity() {

    lateinit var binding: SigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SigninBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signin.setOnClickListener{
            signIn()
        }

        binding.altSignup.setOnClickListener{
            signUp()
        }
    }


    private fun signIn() {
        val intent = Intent(this, FragmentHolder::class.java)
        startActivity(intent)
    }

    private fun signUp() {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }

}