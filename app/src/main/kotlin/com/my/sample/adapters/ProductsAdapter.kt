package com.my.sample.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.my.sample.R
//import android.content.Context
import com.my.sample.extensions.isEmpty
import com.my.sample.extensions.bindImage
import com.my.sample.extensions.showSnackBar
import com.my.sample.extensions.str
import com.my.sample.models.Product

class ProductsAdapter(private val pList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        println(Log.i("Products Count", pList.size.str))
        return pList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = pList[position]

        holder.imageView =
            holder.imageView.bindImage(if (product.thumbnail.isEmpty) product.images[0] else product.thumbnail)

        holder.imageView.adjustViewBounds = true

        // sets the text to the textview from our itemHolder class
        holder.textViewTitle.text = product.title

        holder.textViewDescription.text = product.description

        holder.categoryButton.text = product.category

        holder.categoryButton.setOnClickListener {
            println(product.description)
            holder.imageView.showSnackBar(product.title, Snackbar.LENGTH_SHORT, product.category, null, null)
        }

        holder.categoryButton.setOnLongClickListener {
            println(product.description)
            false
        }

        holder.textViewDescription.setOnClickListener {
            it.showSnackBar(product.description, Snackbar.LENGTH_LONG, "Replace with your own action", null, null)
        }
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.item_image)
        val textViewTitle: TextView = itemView.findViewById(R.id.title)
        val textViewDescription: TextView = itemView.findViewById(R.id.desc)
        val categoryButton: Button = itemView.findViewById(R.id.category)
    }
}