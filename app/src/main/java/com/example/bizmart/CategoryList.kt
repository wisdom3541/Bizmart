package com.example.bizmart

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bizmart.homeAdapter.ProductCustomAdapter

class CategoryList : Fragment(R.layout.category_page_list) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productView()

    }


     private fun productView() {
        val recyclerview = view?.findViewById<RecyclerView>(R.id.categoryList_recyclerView)
        // this creates a vertical layout Manager
        recyclerview?.layoutManager = LinearLayoutManager(view?.context)

//        val horizontalLayoutManagaer =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        recyclerview?.layoutManager = horizontalLayoutManagaer


        // ArrayList of class ItemsViewModel
        val data2 = ArrayList<data2>()

        // This loop will create the category views
        for (i in 1..10) {
            data2.add(
                data2(
                    R.drawable.photography_img,
                    "Bizmart",
                    "An E-directory for Products and Sercvices"
                )
            )
        }


        // This will pass the ArrayList to our Adapter
        val adapter = ProductCustomAdapter(data2)

        // Setting the Adapter with the recyclerview
        recyclerview?.adapter = adapter

    }

    fun nextactivity(){
        val intent = Intent(this.context, SignupSignin::class.java)
        startActivity(intent)    }

}
