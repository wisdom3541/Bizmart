package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bizmart.data.Data3
import com.example.bizmart.homeAdapter.CategoryListAdapter
import com.example.bizmart.homeAdapter.ProductCustomAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryList : AppCompatActivity() {

    private val db = Firebase.firestore

    //  private var dataList : MutableMap<String, Any>? = null
    private lateinit var recyclerview: RecyclerView
    private lateinit var loadingPage: ImageView
    private lateinit var adapter: CategoryListAdapter
    private lateinit var data: ArrayList<Data3>
    lateinit var category : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_page_list)

        // this creates a vertical layout Manager

        recyclerview = findViewById(R.id.categoryList_recyclerView)
        loadingPage = findViewById(R.id.loadingPage)

        recyclerview.layoutManager = LinearLayoutManager(this)

        category = intent?.extras?.getString(TITLE).toString()
        val title = findViewById<TextView>(R.id.title)

        title.text = category
        Log.d("TAG cat", category)

        // ArrayList of class ItemsViewModel
        data = ArrayList<Data3>()

        // This loop will create the default category views
//        for (i in 1..10) {
//            data.add(
//                data2(
//                    R.drawable.photography_img,
//                    "Bizmart",
//                    "An E-directory for Products and Sercvices",
//                    3F
//                )
//            )
//        }

        // This will pass the ArrayList to our Adapter
        adapter = CategoryListAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        loadingPage.visibility = View.VISIBLE


        getData()





    }

    private fun getData() {

        val ref = db.collection("bizmart").document("categories").collection(category.lowercase().filter { !it.isWhitespace() })
        val list = ArrayList<Data3>()

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
                                    Data3(
                                        R.drawable.photography_img,
                                        n,
                                        d,
                                        r,
                                        category

                                    )
                                )

                                Log.d("TAG data", list.toString())
                                //clearing the placeholder data and feeding it the result from the Database
                                data.clear()
                                data.addAll(list)
                                adapter.notifyDataSetChanged()
                                 loadingPage.visibility = View.INVISIBLE
                            }
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    companion object {

        const val TITLE = "category"

    }
}


