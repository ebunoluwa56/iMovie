package com.iyanuoluwa.imovie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iyanuoluwa.imovie.DetailsActivity
import com.iyanuoluwa.imovie.R
import com.iyanuoluwa.imovie.api.Result

class MovieAdapter(var context: Context,
                   var titles: List<String>,
                   var image: List<String>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movies_list, parent, false)
        return ViewHolder(itemView, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.names.text = titles[position]
        Glide.with(holder.images)
                .load(image[position])
                .into(holder.images)
    }

    override fun getItemCount(): Int {
        return titles.size
    }


    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView = itemView.findViewById(R.id.movie_image)
        var names: TextView = itemView.findViewById(R.id.movie_name)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent = Intent(context, DetailsActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}