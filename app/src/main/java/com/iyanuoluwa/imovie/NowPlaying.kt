package com.iyanuoluwa.imovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iyanuoluwa.imovie.adapter.MovieAdapter
import com.iyanuoluwa.imovie.api.MovieJson
import com.iyanuoluwa.imovie.data.ApiNowPlaying
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// https://api.themoviedb.org/3/movie/now_playing?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US

const val BASE_URL = "https://api.themoviedb.org/3/"

class NowPlaying : Fragment() {

    private var textView: TextView? = null
    private var playingRecyclerView: RecyclerView? =null
    private var gridLayoutManager: GridLayoutManager? = null
    private var titleList = mutableListOf<String>()
    private var imageList = mutableListOf<String>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        textView = view.findViewById(R.id.playing)
        playingRecyclerView = view.findViewById(R.id.playing_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        return view

    }

    private fun setUpRecyclerView() {
        playingRecyclerView?.layoutManager = gridLayoutManager
        playingRecyclerView?.adapter = context?.let { MovieAdapter(it, titleList, imageList) }
    }

    private fun addToList(title: String, image: String) {
        titleList.add(title)
        imageList.add(image)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovies()
    }

    private fun getMovies() {
        val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiNowPlaying::class.java)

        val data = api.getMoviesPlaying()
        data.enqueue(object : Callback<MovieJson?> {
            override fun onResponse(call: Call<MovieJson?>, response: Response<MovieJson?>) {
                val responseBody = response.body()!!
                for (movies in responseBody.results) {
                    Log.i("PlayingFragment", "Result = $movies")
                    addToList(movies.title, "http://image.tmdb.org/t/p/w500${movies.posterPath}")
                }
                setUpRecyclerView()
            }

            override fun onFailure(call: Call<MovieJson?>, t: Throwable) {
                Log.e("NowPlayingFragment", t.toString())
            }
        })

    }


}