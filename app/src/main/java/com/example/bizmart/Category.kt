package com.example.bizmart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bizmart.data.Datasource

class Category : Fragment(R.layout.category_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //calling view method
        val data = Datasource().loadText()
        val recyclerView = view.findViewById<RecyclerView>(R.id.category_recycleView)

        recyclerView?.adapter = CategoryAdapter(context,data)
        recyclerView?.setHasFixedSize(true)

    }

//    private fun categoryView() {
//
//
//
//
////        val recyclerview = view?.findViewById<RecyclerView>(R.id.category_recycleView)
////        // this creates a vertical layout Manager
////        recyclerview?.layoutManager = LinearLayoutManager(view?.context)
////
////
////        //this creates a horinzontal layout manager
//////       val horizontalLayoutManagaer =
//////            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//////        recyclerview?.layoutManager = horizontalLayoutManagaer
////
////
////        // ArrayList of class ItemsViewModel
////        val data = ArrayList<category_data>()
////
////        // This loop will create the category views
////        val res: Resources = resources
////        val list = res.getStringArray(R.array.categoryPage_list)
////
////        for (value in list) {
////            data.add(category_data(value))
////        }
////
////
////        // This will pass the ArrayList to our Adapter
////        val adapter = CategoryAdapter(data,data)
//////
//////        // Setting the Adapter with the recyclerview
////        recyclerview?.adapter = adapter
//    }
}