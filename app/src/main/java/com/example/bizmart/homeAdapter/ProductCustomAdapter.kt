package com.example.bizmart.homeAdapter

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.bizmart.BusinessPage
import com.example.bizmart.CategoryList
import com.example.bizmart.R
import com.example.bizmart.data2

class ProductCustomAdapter(private val mList: List<data2>) :
    RecyclerView.Adapter<ProductCustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.design2, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]


        val context = holder.itemView.context
        val intent = Intent(context, BusinessPage::class.java)


        holder.itemView.setOnClickListener {
            intent.putExtra("title", holder.textView.text.toString())
            //category = it.findViewById<TextView>(R.id.title)

            context.startActivity(intent)
        }

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(item.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = item.text
        holder.des.text = item.description
        holder.rating.rating = item.rating

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.product_category_image)
        val textView: TextView = itemView.findViewById(R.id.product_category_title)
        val des: TextView = itemView.findViewById(R.id.description)
        val rating: RatingBar = itemView.findViewById(R.id.ratingBar2)


    }
}
