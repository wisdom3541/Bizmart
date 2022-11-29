package com.example.bizmart

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val context: Context?, private val mList: List<category_data>) :
    RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(R.id.category_label)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.category_design, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = mList[position]
        val context = holder.itemView.context
        val intent = Intent(context, BusinessPage::class.java)

        intent.putExtra("title", holder.textView.text.toString())
        holder.textView.text = context?.resources?.getString(item.text)
        holder.itemView.setOnClickListener {
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }


}




