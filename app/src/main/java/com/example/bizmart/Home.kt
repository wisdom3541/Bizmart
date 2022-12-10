package com.example.bizmart


import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bizmart.homeAdapter.CustomAdapter
import com.example.bizmart.homeAdapter.ProductCustomAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Home : Fragment(R.layout.home_page) {

    private val db = Firebase.firestore
    private val data2 = ArrayList<data2>()
    private val adapter = ProductCustomAdapter(data2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //calling views method
        categoryView()
        productView()

    }

    private fun categoryView() {
        val recyclerview = view?.findViewById<RecyclerView>(R.id.category_view)

        //this creates a horinzontal layout manager
        val horizontalLayoutManagaer =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerview?.layoutManager = horizontalLayoutManagaer


        // ArrayList of class ItemsViewModel
        val data = ArrayList<data1>()

        // This loop will create the category views
        val res: Resources = resources

        data.add(data1(R.drawable.food, "Food"))
        data.add(data1(R.drawable.home, "Home"))
        data.add(data1(R.drawable.travel, "Travel"))
        data.add(data1(R.drawable.photography, "Photography"))
        data.add(data1(R.drawable.realestate, "Real Estate"))


        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview?.adapter = adapter
    }


    private fun productView() {
        val recyclerview = view?.findViewById<RecyclerView>(R.id.product_view)
        // this creates a vertical layout Manager
        recyclerview?.layoutManager = LinearLayoutManager(view?.context)

        // ArrayList of class ItemsViewModel


        // This loop will create the category views
        for (i in 1..10) {
            data2.add(
                data2(
                    R.drawable.photography_img,
                    "Bizmart",
                    "An E-directory for Products and Sercvices",
                    3F
                )
            )
        }




        // Setting the Adapter with the recyclerview
        recyclerview?.adapter = adapter


        //Retrieve data from DB
        getData()


    }

    private fun getData() {

        val loading = view?.findViewById<ImageView>(R.id.loadingPage)

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
                                val r = document.data?.get("rating").toString()?.toFloat()
                                val d = document.data?.get("description").toString()
                                Log.d("TAG", n)

                                //passing result to new list
                                list.add(
                                    data2(
                                        R.drawable.photography_img,
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
                                loading?.visibility = View.INVISIBLE
                            }
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

}