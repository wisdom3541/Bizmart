package com.example.bizmart

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bizmart.databinding.AddBusinessBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.File

class AddBusiness : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: AddBusinessBinding
    private lateinit var name: EditText
    private lateinit var cat: Spinner
    private lateinit var des: EditText
    private lateinit var addy: EditText
    private lateinit var phone: EditText
    private lateinit var abt: EditText
    private lateinit var rating: EditText
    private lateinit var select: Button
    private lateinit var bar : ProgressBar
    private lateinit var add: Button
    private lateinit var container :    ConstraintLayout
    private lateinit var selectedCategory: String
    private lateinit var imageUri: Uri
    private var imgStatus : Boolean = false
    private val PICK_IMAGE = 100
    private lateinit var uploadTask: UploadTask
    private var storageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //hooks
        name = binding.Bname
        cat = binding.spinner
        des = binding.description
        addy = binding.address
        phone = binding.phoneNum
        abt = binding.aboutUs
        rating = binding.rating
        add = binding.addBiz
        select = binding.Bimg
        bar =  binding.progressbar
        container =  binding.container

        add.setOnClickListener {
            if (name.text.isNullOrEmpty() || des.text.isNullOrEmpty() || addy.text.isNullOrEmpty() || phone.text.isNullOrEmpty() || abt.text.isNullOrEmpty() || rating.text.isNullOrEmpty() || selectedCategory.isEmpty() || !imgStatus) {

                val bar =
                    Snackbar.make(binding.root, "Please Fill in All Details", Snackbar.LENGTH_SHORT)
                bar.setBackgroundTint(Color.RED)
                bar.show()

            }else if (des.text.toString().length > 200) {



            }else
             {
                val id = name.text.toString().lowercase().filter { !it.isWhitespace() }
                val name = name.text.toString()
                val category = selectedCategory
                val description = des.text.toString()
                val address = addy.text.toString()
                val phone = phone.text.toString()
                val aboutUs = abt.text.toString()
                val rat = rating.text.toString().toFloat()

                Log.d(
                    "Details: ",
                    id + name + category + description + address + phone + aboutUs + rat + selectedCategory + imageUri.toString()
                )

                loadDB(id, name, category, rat, address, phone, description, aboutUs)

                add.isClickable = false
                disableWidgets()
                bar.visibility = View.VISIBLE
                uploadImg()

                val bar = Snackbar.make(binding.root, "Adding Business...", Snackbar.LENGTH_SHORT)
                bar.setBackgroundTint(Color.BLACK)
                bar.show()

            }
        }


        select.setOnClickListener {

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE)

        }

        val spinner: Spinner = binding.spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent?.getItemAtPosition(position).toString()
        Log.d("Tag", selectedCategory)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //do nothing
    }

    private fun loadDB(
        id: String,
        n: String,
        cat: String,
        rat: Float,
        address: String,
        phone: String,
        description: String,
        aboutUs: String
    ) {
        val db = Firebase.firestore

        val data = hashMapOf(
            "name" to n,
            "category" to cat,
            "rating" to rat,
            "address" to address,
            "phone" to phone,
            "description" to description,
            "aboutUs" to aboutUs
        )

        val category = db.collection("bizmart").document("categories")
            .collection(cat.lowercase().filter { !it.isWhitespace() })

        // Add a new document with an ID specifies
        category.document(id).set(data)

        //for loading...call method
        //loadDB(i2,n,c,rat.toFloat(),address1,phone1,des,aboutUs1)

    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            //mageView.setImageURI(data?.data) // handle chosen image
            imgStatus = true
            imageUri = data?.data!!
            val file = imageUri.path?.let { File(it) }
            Log.d("imag uri ", imageUri.toString())
            select.text = name.text.toString().lowercase().filter { !it.isWhitespace() }


        }
    }

    private fun uploadImg() {

        val id = name.text.toString().lowercase().filter { !it.isWhitespace() }
        val cat = selectedCategory.lowercase().filter { !it.isWhitespace() }
        val riversRef = storageRef.child("$cat/$id.jpeg")
        uploadTask = riversRef.putFile(imageUri)

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Log.d("Tag", "Unsuccessful upload")
            val bar = Snackbar.make(binding.root, "Error adding Business!!", Snackbar.LENGTH_SHORT)
            bar.setBackgroundTint(Color.RED)
            bar.show()
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
            val bar = Snackbar.make(binding.root, "Business Added !!", Snackbar.LENGTH_SHORT)
            bar.setBackgroundTint(Color.BLACK)
            bar.show()

            val timer = object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    //do nothing
                }

                override fun onFinish() {
                    finish()
                }
            }
            timer.start()


        }
    }

    private fun disableWidgets(){

        name.isClickable = false
        cat.isClickable = false
        des.isClickable = false
        addy.isClickable = false
        phone.isClickable = false
        abt.isClickable = false
        rating.isClickable = false
        select.isClickable = false
        container.isClickable = false

    }


}