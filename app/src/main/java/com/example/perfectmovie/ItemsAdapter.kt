package com.example.perfectmovie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemsAdapter(
    private val context: Context,
    private val items: List<ItemOfList>,
    val listener: (ItemOfList) -> Unit
) : RecyclerView.Adapter<ItemsAdapter.ImageViewHolder>() {
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgSrc = view.findViewById<ImageView>(R.id.img)
        val title = view.findViewById<TextView>(R.id.title)
        val description = view.findViewById<TextView>(R.id.description)
        val date = view.findViewById<TextView>(R.id.release_date)
        val rating = view.findViewById<TextView>(R.id.rating)
        fun bindView(item: ItemOfList, listener: (ItemOfList) -> Unit) {
            Picasso.get().load(item.Poster_path).fit().placeholder(R.drawable.download).into(imgSrc)
            title.text = item.Title
            description.text = item.Overview
            date.text = item.Release_date
            rating.text = item.Vote_average
            itemView.setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(items[position], listener)
    }

}