package com.iyanuoluwa.imovie.ui.details

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iyanuoluwa.imovie.R
import com.iyanuoluwa.imovie.ui.main.MovieViewModel
import com.iyanuoluwa.imovie.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private var plotTextView: TextView? = null
    private var titleTextView: TextView? = null
    private var movieImageView: ImageView? = null
    private var movieImageViewBackground: ImageView? = null
    private var castAdapter: CastAdapter? = null
    private var recyclerView: RecyclerView? = null
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        plotTextView = findViewById(R.id.movie_plot)
        titleTextView = findViewById(R.id.movie_title)
        movieImageView = findViewById(R.id.movie_details_image)
        movieImageViewBackground = findViewById(R.id.movie_details_image_background)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.navigationBarColor = this.resources.getColor(com.google.android.material.R.color.design_default_color_primary_variant)
        }

        getCredits()

        val plot = intent.getStringExtra("plot")
        plotTextView?.text = plot
        val title = intent.getStringExtra("title")
        titleTextView?.text = title
        val image = intent.getStringExtra("image")
        Glide.with(this)
            .load(image)
            .apply(RequestOptions().error(R.drawable.no_image))
            .into(movieImageView!!)
        Glide.with(this)
            .load(image)
            .into(movieImageViewBackground!!)
        recyclerView = findViewById(R.id.cast_recycler_view)
        recyclerView?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castAdapter = CastAdapter(this, mutableListOf())
        recyclerView?.adapter = castAdapter
    }

    private fun getCredits() {
        val id = intent.getIntExtra("id", 0)
        movieViewModel.getCast(id).observe(this) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    castAdapter?.cast?.addAll(it.data)
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
                is Resource.Failure -> {
                    Toast.makeText(this, "Unable to fetch details", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}