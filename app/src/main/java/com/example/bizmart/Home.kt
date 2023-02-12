package com.example.bizmart


import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bizmart.homeAdapter.CustomAdapter
import com.example.bizmart.homeAdapter.ProductCustomAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class Home : Fragment(R.layout.home_page) {

    private val db = Firebase.firestore
    private val data2 = ArrayList<data2>()
    private val adapter = ProductCustomAdapter(data2)
    private val storageRef = Firebase.storage.reference
    private lateinit var img : ImageView
    val oneMb: Long = 1024 * 1024
    private lateinit var image: StorageReference
    private lateinit var dataImg : Bitmap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //calling views method
        categoryView()
        productView()

        img= view.findViewById<ImageView>(R.id.loading)
        img.visibility= View.VISIBLE

        //back pressed handler
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("TAG", "Fragment back pressed invoked")

                    // Do custom work here
                    val builder = AlertDialog.Builder(context)
                    //set title for alert dialog
                    builder.setTitle("Log out")
                    //set message for alert dialog
                    builder.setMessage("Do you want to log out? ")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)

                    //performing positive action
                    builder.setPositiveButton("Yes") { _, _ ->
                        Toast.makeText(context, "Logging Out...", Toast.LENGTH_LONG).show()
                        Snackbar.make(view, "Logging Out...", Snackbar.LENGTH_SHORT).show()
                        signOut()
                        activity?.finish()
                    }
                    //performing cancel action
//                    builder.setNeutralButton("Cancel") { _, _ ->
//                        Toast.makeText(
//                            context,
//                            "clicked cancel\n operation cancel",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
                    //performing negative action
                    builder.setNegativeButton("No") { _, _ ->
                        //do Nothing
                       // Toast.makeText(context, "clicked No", Toast.LENGTH_LONG).show()
                    }
                    // Create the AlertDialog
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    // if you want onBackPressed() to be called as normal afterwards
                }
            }
            )
        //end back pressed handler

    }

    private fun categoryView() {
        val recyclerview = view?.findViewById<RecyclerView>(R.id.category_view)

        //this creates a horizontal layout manager
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerview?.layoutManager = horizontalLayoutManager


        // ArrayList of class ItemsViewModel
        val data = ArrayList<data1>()

        // This loop will create the category views
        val res: Resources = resources

        data.add(data1(R.drawable.food, "Food"))
        data.add(data1(R.drawable.travel, "Travel"))
        data.add(data1(R.drawable.photography, "Photography"))
        data.add(data1(R.drawable.realestate, "Real Estate"))
        data.add(data1(R.drawable.finance, "Finance"))


        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview?.adapter = adapter


    }

    private fun productView() {
        val recyclerview = view?.findViewById<RecyclerView>(R.id.product_view)


        // this creates a vertical layout Manager
        val verticalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview?.layoutManager = verticalLayoutManager

        // ArrayList of class ItemsViewModel


        // This loop will create the category views


        // Setting the Adapter with the recyclerview
        recyclerview?.adapter = adapter


        //Retrieve data from DB
        getData()


    }

    private fun getData() {

        //val loading = view?.findViewById<ImageView>(R.id.loadingPage)

        val ref = db.collection("popular")
        val list = ArrayList<data2>()

        ref.get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val id = doc.id

                    // getting business Info
                    ref.document(id)
                        .get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                val n = document.data?.get("name").toString()
                                val r = document.data?.get("rating").toString().toFloat()
                                val d = document.data?.get("description").toString()
                                val cat =  document.data?.get("category").toString()
                                Log.d("TAG", cat)
                                val catT = cat.lowercase().filter { !it.isWhitespace() }
                                Log.d("TAG", catT)
                                loadImg(catT, id)

                                //load bitmap
                                //passing result to the image for the adapter
                                image.getBytes(oneMb).addOnSuccessListener {
                                    // Data for "images reference" is returned, use this as needed
                                    dataImg = BitmapFactory.decodeByteArray(it,0, it.size)

                                    //passing result to new list
                                    list.add(
                                        data2(
                                            dataImg,
                                            n,
                                            d,
                                            r
                                        )
                                    )

                                    Log.d("TAG data", list.toString())
                                    //clearing the placeholder data and feeding it the result from the Database
                                    data2.clear()
                                    data2.addAll(list)
                                    adapter.notifyDataSetChanged()
                                    img.visibility = View.GONE


                                }.addOnFailureListener {
                                    Log.d("Tag E: ", it.toString())
                                    // Handle any errors
                                }





                                //   loading?.visibility = View.GONE
                            }
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    private fun loadImg(c: String, i: String) {

        //val c =  category.lowercase().filter { !it.isWhitespace() }
        // val i =  id.lowercase().filter { !it.isWhitespace() }
        val image1 = "$c/$i.jpeg"
        Log.d("tag", image1)
        image = storageRef.child(image1)
//        Category = db.collection("bizmart").document("categories")
//            .collection(category.lowercase().filter { !it.isWhitespace() })
//        Log.d("idTAG", "category: $Category")
    }

    private fun signOut() {

        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, SignIn::class.java)
        startActivity(intent)
    }


}


