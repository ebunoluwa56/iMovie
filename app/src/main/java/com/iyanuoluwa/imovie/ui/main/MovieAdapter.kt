package com.iyanuoluwa.imovie.ui.main

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
import com.iyanuoluwa.imovie.R
import com.iyanuoluwa.imovie.data.model.Result
import com.iyanuoluwa.imovie.ui.details.DetailsActivity

class MovieAdapter(
    val context: Context,
    var movies: MutableList<Result>
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movies_list, parent, false)
        return ViewHolder(itemView, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.names.text = movies[position].title
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.images)
            .load("https://image.tmdb.org/t/p/w500${movies[position].posterPath}")
            .apply(
                RequestOptions().placeholder(circularProgressDrawable)
                    .error(R.drawable.no_image)
            )
            .fitCenter()
            .into(holder.images)
    }

    override fun getItemCount() = movies.size


    inner class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView = itemView.findViewById(R.id.movie_image)
        var names: TextView = itemView.findViewById(R.id.movie_name)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(
                    "image",
                    "https://image.tmdb.org/t/p/w500${movies[position].posterPath}"
                )
                intent.putExtra("plot", movies[position].overview)
                intent.putExtra("title", movies[position].title)
                intent.putExtra("id", movies[position].id)
                context.startActivity(intent)
            }
        }
    }
}