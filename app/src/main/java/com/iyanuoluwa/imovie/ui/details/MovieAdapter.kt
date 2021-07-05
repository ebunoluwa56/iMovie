package com.iyanuoluwa.imovie.ui.details

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iyanuoluwa.imovie.DetailsActivity
import com.iyanuoluwa.imovie.R

class MovieAdapter(
    var context: Context,
    var titles: List<String>,
    var imageList: List<String>,
    var plot: List<String>,
    var id: List<Int>
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movies_list, parent, false)
        return ViewHolder(itemView, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.names.text = titles[position]
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.images)
            .load(imageList[position])
            .apply(
                RequestOptions().placeholder(circularProgressDrawable)
                    .error(R.drawable.no_image)
            )
            .fitCenter()
            .into(holder.images)
    }

    override fun getItemCount(): Int {
        return titles.size
    }


    inner class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView = itemView.findViewById(R.id.movie_image)
        var names: TextView = itemView.findViewById(R.id.movie_name)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("image", imageList[position])
                intent.putExtra("plot", plot[position])
                intent.putExtra("title", titles[position])
                intent.putExtra("id", id[position])
                context.startActivity(intent)
            }
        }
    }
}