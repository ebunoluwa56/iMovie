package com.iyanuoluwa.imovie.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iyanuoluwa.imovie.R

class CastAdapter(
    var context: Context,
    var castNames: List<String>,
    var castImages: List<String>
) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        var names: TextView = itemView.findViewById(R.id.cast_name)
        var images: ImageView = itemView.findViewById(R.id.cast_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cast_list, parent, false)
        return ViewHolder(itemView, context)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.names.text = castNames[position]
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(Color.WHITE)
        circularProgressDrawable.start()
        Glide.with(holder.images)
            .load(castImages[position])
            .apply(
                RequestOptions().placeholder(circularProgressDrawable)
                    .error(R.drawable.ic_launcher_foreground)
            )
            .fitCenter()
            .into(holder.images)
    }

    override fun getItemCount(): Int {
        return castNames.size
    }

}