package com.example.bizmart.homeAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bizmart.CategoryList
import com.example.bizmart.R
import com.example.bizmart.data1

class CustomAdapter(private val mList: List<data1>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        val context = holder.itemView.context
        val intent = Intent(context, CategoryList::class.java)


        holder.itemView.setOnClickListener {
            intent.putExtra("category", holder.textView.text.toString())
            context.startActivity(intent)
        }

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(item.image)
        // sets the text to the textview from our itemHolder class
        holder.textView.text = item.text

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.category_image)
        val textView: TextView = itemView.findViewById(R.id.category_title)
    }
}
