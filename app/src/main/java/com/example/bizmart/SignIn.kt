package com.example.bizmart

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SigninBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {

    lateinit var binding: SigninBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signin.setOnClickListener{
            signIn()
        }

        binding.altSignup.setOnClickListener{
            signUp()
        }
    }


    private fun signIn() {
       login()
    }

    private fun signUp() {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
        finish()
    }

    private fun login(){
        auth.signInWithEmailAndPassword(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")

                    Toast.makeText(this ,"SignIn successful", Toast.LENGTH_LONG ).show()
                    val bar =
                        Snackbar.make(binding.root, "SignIn successful", Snackbar.LENGTH_LONG)
                    bar.setBackgroundTint(Color.GREEN)
                    bar.show()
                    val user = auth.currentUser
                    updateUI(user)

                    val intent = Intent(this, FragmentHolder::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Incorrect E-mail or Password",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {

        Log.d("This is user", user.toString())

    }

}