package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.databinding.SignupSigninBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupSignin : AppCompatActivity() {

    lateinit var binding: SignupSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignupSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signINBTN.setOnClickListener { signIn() }

        binding.signUpBTN.setOnClickListener { signUp()}

    }

    private fun signIn() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
    }

    private fun signUp() {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }


    private fun testDB() {
        val db = Firebase.firestore

        val data = hashMapOf(
            "name" to "Chicken Republic",
            "category" to "food",
            "rating" to 5,
            "address" to "22 Rd, Festac Town",
            "phone" to "080 9016 5926",
            "description" to "A 5 - star fast food Restaurant",
            "aboutUs" to "Chicken Republic is a proudly Nigerian brand that comes from humble beginnings. We opened our first Chicken Republic restaurant in Lagos in 2004 and are presently trading in over 150 locations across Nigeria and Ghana, with plans to operate 430 restaurants by the end of 2024. Chicken Republic is a committed supporter of Nigerian production. Most of our products are sourced directly from local, Nigerian suppliers. Our philosophy is “People Capability Always”. We pride ourselves on being an equal opportunity employer, with a strong focus on creating opportunities for women and young adults."

        )

        val category = db.collection("popular")

        // Add a new document with a ID specifies
        category.document("chickenrepublic").set(data)
        //category.collection("photography").document("apexphotostudios").set(data);


    }

}