package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getStarted.setOnClickListener { nextActivity() }

        auth = Firebase.auth

        val userInfo = Firebase.auth.currentUser
        userInfo?.let {
            // Name, email address, and profile photo Url
            val name = userInfo.displayName
            val email = userInfo.email
            val photoUrl = userInfo.photoUrl

            // Check if user's email is verified
            //val emailVerified = userInfo.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = userInfo.uid

            Log.d("Info", name.toString() + email.toString() + uid)


        }
    }

    private fun nextActivity(){
        val intent = Intent(this, SignupSignin::class.java)
        startActivity(intent)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            // reload();
            val intent = Intent(this, FragmentHolder::class.java)
            startActivity(intent)
        }else{
            binding.getStarted.visibility = View.VISIBLE
        }
    }


}