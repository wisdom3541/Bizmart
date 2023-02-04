package com.example.bizmart

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bizmart.databinding.ProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class Profile : Fragment(R.layout.profile) {

    lateinit var name: EditText
    lateinit var email: EditText
    private lateinit var title: TextView
    private lateinit var editProfile: Button
    private lateinit var addBusiness: Button
    private val user = Firebase.auth.currentUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = view.findViewById(R.id.name)
        email = view.findViewById(R.id.email) as EditText
        title = view.findViewById(R.id.title) as TextView
        editProfile = view.findViewById(R.id.edit) as Button
        addBusiness =  view.findViewById(R.id.addBusinessBTN) as Button

        addBusiness.setOnClickListener {
            val intent = Intent(view.context ,AddBusiness::class.java)
            startActivity(intent)
        }



        editProfile.setOnClickListener {
            if (editProfile.text == "Edit Profile") {
                editProfile()
            } else {
                profileSet()
            }


        }


    }

    private fun editProfile() {

        name.inputType = InputType.TYPE_CLASS_TEXT

        name.isEnabled = true
        name.requestFocus()

        title.text = "Update Profile"
        editProfile.text = "Update INFO"


    }

    private fun profileSet() {

        name.inputType = InputType.TYPE_NULL
        email.inputType = InputType.TYPE_NULL

        name.isEnabled = false
        email.isEnabled = false

        val n = name.text.toString()

        val profileUpdates = userProfileChangeRequest {
            displayName = n
            photoUri = Uri.parse("")
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view?.let {
                        val bar = Snackbar.make(requireView(), "Profile Updated", Snackbar.LENGTH_SHORT)
                        bar.setBackgroundTint(Color.BLACK)
                        bar.show()

                    }
                    Log.d("success", "User profile updated.")
                    name.setText(n)

                }
            }


        title.text = "Profile"
        editProfile.text = "Edit Profile"

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in and get values
        if (user != null) {
            val n = user.displayName
            val e = user.email

            name.setText(n)
            email.setText(e)


        }

    }
}