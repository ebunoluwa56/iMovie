package com.iyanuoluwa.imovie

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.iyanuoluwa.imovie.api2.CreditsJSon
import com.iyanuoluwa.imovie.data.ApiCredits
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        getCredits()
    }

    private fun getCredits() {
        val id = intent.getIntExtra("id", 460465)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCredits::class.java)

        val data = api.getMovieCredits(id)
        data.enqueue(object : Callback<CreditsJSon?> {
            override fun onResponse(call: Call<CreditsJSon?>, response: Response<CreditsJSon?>) {
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    for (credits in responseBody.cast) {
                        Log.i("DetailsActivity", "Cast = ${credits.name}")
                    }
                }
            }

            override fun onFailure(call: Call<CreditsJSon?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}