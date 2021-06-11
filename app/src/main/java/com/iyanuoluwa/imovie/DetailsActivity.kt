package com.iyanuoluwa.imovie

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iyanuoluwa.imovie.adapter.CastAdapter
import com.iyanuoluwa.imovie.api2.Credits
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
    private var movieImageViewBackground: ImageView? = null
    private var imageList = mutableListOf<String>()
    private var castList = mutableListOf<String>()
    private var recyclerView: RecyclerView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        plotTextView = findViewById(R.id.movie_plot)
        titleTextView = findViewById(R.id.movie_title)
        movieImageView = findViewById(R.id.movie_details_image)
        movieImageViewBackground = findViewById(R.id.movie_details_image_background)

        val plot = intent.getStringExtra("plot")
        plotTextView?.text = plot
        val title = intent.getStringExtra("title")
        titleTextView?.text = title
        val image = intent.getStringExtra("image")
        Glide.with(this)
            .load(image)
            .apply(RequestOptions().error(R.drawable.ic_launcher_foreground))
            .into(movieImageView!!)
        Glide.with(this)
            .load(image)
            .into(movieImageViewBackground!!)
        getCredits()

        recyclerView = findViewById(R.id.cast_recycler_view)
    }

    private fun setUpRecyclerView() {
        recyclerView?.adapter = CastAdapter(this, castList, imageList)
    }

    private fun addToList(name: String, image: String) {
        imageList.add(image)
        castList.add(name)
    }

    private fun getCredits() {
        val id = intent.getIntExtra("id", 460465)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCredits::class.java)

        val data = api.getMovieCredits(id)
        data.enqueue(object : Callback<Credits?> {
            override fun onResponse(call: Call<Credits?>, response: Response<Credits?>) {
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    for (credits in responseBody.cast) {
                        Log.i("DetailsActivity", "Cast = ${credits.name}")
                        addToList(credits.name, "https://image.tmdb.org/t/p/w500${credits.profilePath}")
                    }
                }
                setUpRecyclerView()
            }

            override fun onFailure(call: Call<Credits?>, t: Throwable) {
                Log.e("DetailsActivity", t.toString())
            }
        })
    }
}