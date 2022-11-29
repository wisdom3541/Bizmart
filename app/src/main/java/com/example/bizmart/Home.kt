package com.example.bizmart


import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bizmart.homeAdapter.CustomAdapter
import com.example.bizmart.homeAdapter.ProductCustomAdapter


class Home : Fragment(R.layout.home_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //calling views method
        categoryView()
        productView()

    }

    private fun categoryView() {
        val recyclerview = view?.findViewById<RecyclerView>(R.id.category_view)
        // this creates a vertical layout Manager
        //recyclerview?.layoutManager = LinearLayoutManager(view?.context)


        //this creates a horinzontal layout manager
        val horizontalLayoutManagaer =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerview?.layoutManager = horizontalLayoutManagaer


        // ArrayList of class ItemsViewModel
        val data = ArrayList<data1>()

        // This loop will create the category views
        val res: Resources = resources
        val appThemeList = res.getStringArray(R.array.category_list)

        for (value in appThemeList) {
            data.add(data1(R.drawable.photography_img, value))
        }


        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview?.adapter = adapter
    }


    private fun productView() {
        val recyclerview = view?.findViewById<RecyclerView>(R.id.product_view)
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

}