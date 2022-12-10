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

        recyclerView?.adapter = CategoryAdapter(context, data)
        recyclerView?.setHasFixedSize(true)

    }
}