package com.example.bizmart

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.bizmart.databinding.BusinessPageBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class BusinessPage : AppCompatActivity() {

    private lateinit var binding: BusinessPageBinding
    private var dataList: MutableMap<String, Any>? = null
    private val db = Firebase.firestore
    private lateinit var id: String
    private lateinit var category: String
    private lateinit var Category : CollectionReference
    private lateinit var businessImage : StorageReference
    private val storageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BusinessPageBinding.inflate(layoutInflater)


        id = intent?.extras?.getString(ID).toString()
        category = intent?.extras?.getString(CATEGORY).toString()
        Log.d("cat correct: ", category)


        getData()

        binding.address.setOnClickListener {

            val text = binding.address.text
            val gmmIntentUri =
                Uri.parse("geo:0,0?q=1600 $text")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)

            }
        }

        binding.phoneNum.setOnClickListener {
            val text = binding.phoneNum.text
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel: $text")
            if (dialIntent.resolveActivity(packageManager) != null) {
                startActivity(dialIntent)
            }
        }


    }

    private fun getData() {


        val name = binding.businessName
        val cat = binding.category
        val address = binding.address
        val phone = binding.phoneNum
        val aboutUs = binding.businessInfo
        val rating = binding.ratingBar



        //val islandRef = storageRef.child("images/island.jpg")

        val ONE_MEGABYTE: Long = 1024 * 1024


//        Log.d("fetched image:", image.toString())
//            Glide.with(this /* context */)
//                .load(image)
//                .into(imageview)


        if(category == "null") {

            Category = db.collection("popular")
            Log.d("idTAG", "Category: $Category")

        }else{
            val c =  category.lowercase().filter { !it.isWhitespace() }
            val i =  id.lowercase().filter { !it.isWhitespace() }
            val image = "$c/$i.jpeg"
            Log.d("tag", image)
            businessImage = storageRef.child(image)
            Category = db.collection("bizmart").document("categories")
                .collection(category.lowercase().filter { !it.isWhitespace() })
            Log.d("idTAG", "category: $Category")

        }


        Category.document(id.lowercase().filter { !it.isWhitespace() })
            .get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    try {
                        // some code
                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                        dataList = document.data

                        name.text = dataList?.get("name").toString()
                        val c = dataList?.get("category").toString()
                        cat.text = "Category: " + c
                        address.text = dataList?.get("address").toString()
                        phone.text = dataList?.get("phone").toString()
                        aboutUs.text = dataList?.get("aboutUs").toString()
                        val rat = document.data?.get("rating").toString()
                        Log.d("TAG4", rat)
                        val r = rat + "F"
                        Log.d("TAG5", r)
                        rating.rating = r.toFloat()

                        val n = dataList?.get("name").toString()
                        val address1 = dataList?.get("address").toString()
                        val phone1 = dataList?.get("phone").toString()
                        val des = dataList?.get("description").toString()
                        val aboutUs1 = dataList?.get("aboutUs").toString()
                        val i2 =  id.lowercase().filter { !it.isWhitespace() }




                        //loading img for when businessPage is called from home
                        if(category == "null"){
                            val cat1 =  c.lowercase().filter { !it.isWhitespace() }
                            val i =  id.lowercase().filter { !it.isWhitespace() }
                            val image = "$cat1/$i.jpeg"
                            businessImage = storageRef.child(image)

                        }

                        //getting img before setting View
                        businessImage.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                            // Data for "image" is returned
                            binding.businessImage.scaleType = ImageView.ScaleType.FIT_XY
                            binding.businessImage.setImageBitmap(byteArrayToBitmap(it))
                        }.addOnFailureListener {
                            Log.d("Tag E: ", it.toString())
                            // Handle any errors
                        }

                        setContentView(binding.root)


                    } catch (e: Exception) {
                        Log.d("Exception: ", e.toString())

                    } finally {

                    }
                } else {
                    Log.d("TAG", "No such document")
//                    val intent = Intent(this, CategoryList::class.java)
//                    intent.putExtra("title", id)
//                    Log.d("TAG error ID correct? :", id)
//                    startActivity(intent)
                }


            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }


    }

    private fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    companion object {

        const val ID = "title"
        const val CATEGORY = "category"

    }

    private fun loadDB(id : String,n: String, cat : String, rat : Float, address: String ,phone: String,description: String,aboutUs: String) {
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

        val category = db.collection("popular")

        // Add a new document with an ID specifies
        category.document(id).set(data)

            //for loading...call method
        //loadDB(i2,n,c,rat.toFloat(),address1,phone1,des,aboutUs1)

    }
}


