package com.example.bizmart

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bizmart.data.Data3
import com.example.bizmart.homeAdapter.CategoryListAdapter
import com.example.bizmart.homeAdapter.ProductCustomAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.collections.ArrayList

class CategoryList : AppCompatActivity() {

    private val db = Firebase.firestore

    //  private var dataList : MutableMap<String, Any>? = null
    private lateinit var recyclerview: RecyclerView
    private lateinit var loadingPage: ImageView
    private lateinit var adapter: CategoryListAdapter
    private lateinit var data: ArrayList<Data3>
    private lateinit var list: ArrayList<Data3>
    private lateinit var image: StorageReference
    private lateinit var dataImg : Bitmap
    private lateinit var filteredList : ArrayList<Data3>
    private lateinit var back : ImageButton
    private lateinit var progressBar: ProgressBar
    lateinit var category: String
    lateinit var searchBox : SearchView
    private val storageRef = Firebase.storage.reference
    val oneMb: Long = 1024 * 1024


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_page_list)

        // this creates a vertical layout Manager

        //initializing var
        recyclerview = findViewById(R.id.categoryList_recyclerView)
        loadingPage = findViewById(R.id.loadingPage)
        searchBox = findViewById(R.id.search)
        progressBar = findViewById(R.id.progressBar)

        back = findViewById(R.id.back)
        filteredList = ArrayList<Data3>()

        recyclerview.layoutManager = LinearLayoutManager(this)



        category = intent?.extras?.getString(TITLE).toString()
        val title = findViewById<TextView>(R.id.title)

        title.text = category
        Log.d("TAG cat", category)

        // ArrayList of class ItemsViewModel
        data = ArrayList<Data3>()

        // This will pass the ArrayList to our Adapter
        adapter = CategoryListAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        loadingPage.visibility = View.VISIBLE


        if (category == "search" ){
            getSearchData()
        }else{
            getData(category)
        }


       back.setOnClickListener{ onBackPressed() }


        //SearchView
        searchBox.clearFocus()
        searchBox.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList.clear()
                adapter.setFilteredList(filteredList)
                filterText(newText)
                return true
            }

        })

    }

    private fun filterText(query: String?) {

        if(query != null){

            for (i in list){
               // Log.d("list value:", i.text)

                if (i.text.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }

            }

            Log.d("list value:", filteredList.toString())
            if (filteredList.isEmpty()){
                Toast.makeText(this, "Sorry...No Business found!!", Toast.LENGTH_SHORT).show()
            }else{

                adapter.setFilteredList(filteredList)
            }
        }

    }

    //getting data per category
    private fun getData(category: String) {

        val ref = db.collection("bizmart").document("categories")
            .collection(category.lowercase().filter { !it.isWhitespace() })
         list = ArrayList<Data3>()

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
                                val cat = document.data?.get("category").toString()
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
                                        Data3(
                                            dataImg ,
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
                                    loadingPage.visibility = View.GONE
                                    progressBar.visibility = View.GONE
                                }.addOnFailureListener {
                                    Log.d("Tag E: ", it.toString())
                                    // Handle any errors
                                }



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

    private fun getSearchData(){
        progressBar.visibility = View.VISIBLE

        val searchList = arrayOf("Food","Travel","Fashion","Photography","Technology",
        "Agriculture","Real Estate","Finance","Sport","Insurance"
            )


        for (i in searchList){
            println(i)
            getData(i)
        }




    }

    companion object {

        const val TITLE = "category"

    }
}


