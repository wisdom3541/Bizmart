package com.example.bizmart

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SignupBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.type.FractionOrBuilder

class Signup : AppCompatActivity() {

    lateinit var binding: SignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fName: EditText
    private lateinit var lName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fName = binding.firstnameEditText
        lName = binding.lastnameEditText

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.signUp.setOnClickListener { signUp() }

        binding.altLogin.setOnClickListener { signIn() }
    }


    private fun signIn() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()
    }

    private fun signUp() {

        val validEmail = isValidEmail(binding.emailEditText.text.toString())

        if (binding.firstnameEditText.text.isNullOrEmpty() || binding.lastnameEditText.text.isNullOrEmpty() || binding.emailEditText.text.isNullOrEmpty() || binding.passwordEditText.text.isNullOrEmpty()) {

            val bar =
                Snackbar.make(binding.root, "Please Fill in All Details", Snackbar.LENGTH_SHORT)
            bar.setBackgroundTint(Color.RED)
            bar.show()
        } else if (validEmail) {
            create()
        } else {
            val bar =
                Snackbar.make(binding.root, "Please Enter A Valid E-mail Address", Snackbar.LENGTH_SHORT)
            bar.setBackgroundTint(Color.RED)
            bar.show()

        }
    }


    private fun create() {

        auth.createUserWithEmailAndPassword(
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    Log.d("User", user.toString())

                    //setting user info

                    val profileUpdates = userProfileChangeRequest {
                        displayName = fName.text.toString() + " " + lName.text.toString()
                        photoUri = Uri.parse("")
                    }

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("success", "User profile updated.")
                            }
                        }

                    //end setting user info


                    //Logging user info
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

                    val intent = Intent(this, FragmentHolder::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Failed", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun updateUI(user: FirebaseUser?) {

    }
}