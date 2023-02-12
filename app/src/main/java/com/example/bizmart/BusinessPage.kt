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
import com.example.bizmart.data.Values
import com.example.bizmart.databinding.BusinessPageBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.ln
import kotlin.properties.Delegates

class BusinessPage : AppCompatActivity() {

    private lateinit var binding: BusinessPageBinding
    private var dataList: MutableMap<String, Any>? = null
    private val db = Firebase.firestore
    private lateinit var id: String
    private lateinit var category: String
    private lateinit var Category : CollectionReference
    private lateinit var businessImage : StorageReference
    private val storageRef = Firebase.storage.reference
    private lateinit var bAddress : String
    private var lat : Double = 0.0
    private var lng : Double = 0.0
    private lateinit var addy: String
    private lateinit var fAddy: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BusinessPageBinding.inflate(layoutInflater)


        id = intent?.extras?.getString(ID).toString()
        category = intent?.extras?.getString(CATEGORY).toString()
        Log.d("cat correct: ", category)




        getData()

        binding.mapImage.setOnClickListener {


            Log.d("latlng", lat.toString() + lng.toString())
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("lat", lat)
            intent.putExtra("lng", lng)
            intent.putExtra("address",bAddress)
            startActivity(intent)

//            val text = binding.address.text
//            val gmmIntentUri =
//                Uri.parse("geo:0,0?q=1600 $text")
//            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//            mapIntent.setPackage("com.google.android.apps.maps")
//            if (mapIntent.resolveActivity(packageManager) != null) {
//                startActivity(mapIntent)
//
//            }
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
                        bAddress = dataList?.get("address").toString()

                        //reverse geocoding
                        editAddress(bAddress)
                        getLatLng()

//                        val phone1 = dataList?.get("phone").toString()
//                        val des = dataList?.get("description").toString()
//                        val aboutUs1 = dataList?.get("aboutUs").toString()
//                        val i2 =  id.lowercase().filter { !it.isWhitespace() }




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
                }


            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }


    }

    private fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    private fun getLatLng(){

        val quotesApi = RetrofitHelper.getInstance().create(GeocodingApi::class.java)

        // launching a new coroutine
        GlobalScope.launch {
            val result = quotesApi.reposList(fAddy)
            if (result != null) {
                // Checking the results

                val value : Values? =  result.body()?.results?.get(0)
                lat = value?.geometry?.location?.lat!!
                lng = value.geometry.location.lng

                Log.d(
                    "Size: ", value.toString() + lat + lng

                )
            }

        }

    }

    private fun editAddress(ad: String) {
        addy = ad.replace("\\s+".toRegex(), "%20").toLowerCase();
        addy = addy.replace("\\.".toRegex(), "").toLowerCase();
        addy = addy.replace(",".toRegex(), "").toLowerCase();
        Log.d("Addy :", addy)


        fAddy = "maps/api/geocode/json?address=$addy&key=${BuildConfig.MAPS_API_KEY}"

        Log.d("Addy :", fAddy)
    }

    companion object {

        const val ID = "title"
        const val CATEGORY = "category"

    }



}


