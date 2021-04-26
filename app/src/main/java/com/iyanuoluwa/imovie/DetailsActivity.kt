package com.iyanuoluwa.imovie

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {

    private var plotTextView: TextView? = null
    private var titleTextView: TextView? = null
    private var movieImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        plotTextView = findViewById(R.id.movie_plot)
        titleTextView = findViewById(R.id.movie_title)
        movieImageView = findViewById(R.id.movie_details_image)

        val plot = intent.getStringExtra("plot")
        plotTextView?.text = plot
        val title = intent.getStringExtra("title")
        titleTextView?.text = title
        val image = intent.getStringExtra("image")
        Glide.with(this)
                .load(image)
                .into(movieImageView!!)
    }
}