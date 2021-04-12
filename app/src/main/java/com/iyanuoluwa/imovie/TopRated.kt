package com.iyanuoluwa.imovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iyanuoluwa.imovie.adapter.MovieAdapter
import com.iyanuoluwa.imovie.api.MovieJson
import com.iyanuoluwa.imovie.data.ApiTopRated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TopRated : Fragment() {

    private var topRatedRecyclerView: RecyclerView? =null
    private var gridLayoutManager: GridLayoutManager? = null
    private var titleList = mutableListOf<String>()
    private var imageList = mutableListOf<String>()
    private var nestedScrollView: NestedScrollView? = null
    private var progressBar: ProgressBar? = null
    private var page: Int = 1
    private var limit: Int = 20


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_top_rated, container, false)
        topRatedRecyclerView = view.findViewById(R.id.top_rated_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        nestedScrollView = view.findViewById(R.id.scroll_view_topRated)
        progressBar = view.findViewById(R.id.progress_bar_topRated)
        return view
    }

    private fun addToList(title: String, image: String) {
        titleList.add(title)
        imageList.add(image)
    }

    private fun setUpRecyclerView() {
        topRatedRecyclerView?.layoutManager = gridLayoutManager
        topRatedRecyclerView?.adapter = context?.let { MovieAdapter(it, titleList, imageList) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTopRatedMovies(page, limit)
        nestedScrollView?.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (scrollY == (v?.getChildAt(0)?.measuredHeight)?.minus(v?.measuredHeight!!)) {
                    page++
                    progressBar?.visibility = View.VISIBLE
                    getTopRatedMovies(page, limit)
                }
            }
        })
    }

    private fun getTopRatedMovies(page: Int, limit: Int) {
        val apiTopRated = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiTopRated::class.java)

        val data = apiTopRated.getTopRatedMovies(page, limit)
        data.enqueue(object : Callback<MovieJson?> {
            override fun onResponse(call: Call<MovieJson?>, response: Response<MovieJson?>) {
                if (response.isSuccessful && response.body() != null) {
                    progressBar?.visibility = View.GONE
                    val responseBody = response.body()!!
                    for (movies in responseBody.results) {
                        Log.i("TopRatedFragment", "Result = $movies")
                        addToList(movies.title, "http://image.tmdb.org/t/p/w500${movies.posterPath}")
                    }
                    setUpRecyclerView()
                }
            }

            override fun onFailure(call: Call<MovieJson?>, t: Throwable) {
                Log.e("TopRatedFragment", t.toString())
            }
        })
    }

}