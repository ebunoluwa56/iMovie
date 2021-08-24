package com.iyanuoluwa.imovie.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iyanuoluwa.imovie.MovieApplication
import com.iyanuoluwa.imovie.R
import com.iyanuoluwa.imovie.data.model.MovieJson
import com.iyanuoluwa.imovie.data.remote.ApiNowPlaying
import com.iyanuoluwa.imovie.ui.main.MainActivity
import com.iyanuoluwa.imovie.ui.main.MovieAdapter
import com.iyanuoluwa.imovie.ui.main.MovieViewModel
import com.iyanuoluwa.imovie.ui.main.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NowPlaying : Fragment() {

    private var textView: TextView? = null
    private var playingRecyclerView: RecyclerView? =null
    private var gridLayoutManager: GridLayoutManager? = null
    private var titleList = mutableListOf<String>()
    private var imageList = mutableListOf<String>()
    private var plotList = mutableListOf<String>()
    private var nestedScrollView: NestedScrollView? = null
    private var progressBar: ProgressBar? = null
    private var page: Int = 1
    private var limit: Int = 20
    private var ids = mutableListOf<Int>()

    private val movieViewModel: MovieViewModel by viewModels {
        ViewModelFactory((requireActivity().application as MovieApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        textView = view.findViewById(R.id.playing)
        playingRecyclerView = view.findViewById(R.id.playing_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        nestedScrollView = view.findViewById(R.id.scroll_view_now)
        progressBar = view.findViewById(R.id.progress_bar_now)
        return view

    }

    private fun setUpRecyclerView() {
        playingRecyclerView?.layoutManager = gridLayoutManager
        playingRecyclerView?.adapter = context?.let { MovieAdapter(it, titleList, imageList, plotList, ids) }
    }

    private fun addToList(title: String, image: String, plot: String, id: Int) {
        titleList.add(title)
        imageList.add(image)
        plotList.add(plot)
        ids.add(id)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovies(page, limit)
        nestedScrollView?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == (v?.getChildAt(0)?.measuredHeight)?.minus(v.measuredHeight)) {
                page++
                progressBar?.visibility = View.VISIBLE
                getMovies(page, limit, false)
            }
        })
    }

    private fun getMovies(page: Int, limit: Int, shouldPersist: Boolean = true) {
        val api = Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiNowPlaying::class.java)

        val data = api.getMoviesPlaying(page, limit)
        data.enqueue(object : Callback<MovieJson?> {
            override fun onResponse(call: Call<MovieJson?>, response: Response<MovieJson?>) {
                if (response.isSuccessful && response.body() != null) {
                    progressBar?.visibility = View.GONE
                    val responseBody = response.body()!!
                    responseBody.results.forEach { movie ->
                        addToList(movie.title, "https://image.tmdb.org/t/p/w500${movie.posterPath}", movie.overview, movie.id)
                    }

                    // Insert movies locally in the db
                    if (shouldPersist) movieViewModel.insertMovies(responseBody.results)

                    setUpRecyclerView()
                }
            }

            override fun onFailure(call: Call<MovieJson?>, t: Throwable) {
                Log.e("NowPlayingFragment", t.toString())
            }
        })

    }


}