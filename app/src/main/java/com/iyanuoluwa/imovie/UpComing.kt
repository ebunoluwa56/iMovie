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
import com.iyanuoluwa.imovie.data.ApiUpcoming
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpComing : Fragment() {

    private var upcomingRecyclerView: RecyclerView? =null
    private var gridLayoutManager: GridLayoutManager? = null
    private var titleList2 = mutableListOf<String>()
    private var imageList2 = mutableListOf<String>()
    private var nestedScrollView: NestedScrollView? = null
    private var progressBar: ProgressBar? = null
    private var page: Int = 1
    private var limit: Int = 20


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_up_coming, container, false)
        upcomingRecyclerView = view.findViewById(R.id.upcoming_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        nestedScrollView = view.findViewById(R.id.scroll_view_upComing)
        progressBar = view.findViewById(R.id.progress_bar_upComing)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUpcomingMovies(page, limit)
        nestedScrollView?.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (scrollY == (v?.getChildAt(0)?.measuredHeight)?.minus(v?.measuredHeight!!)) {
                    page++
                    progressBar?.visibility = View.VISIBLE
                    getUpcomingMovies(page, limit)
                }
            }
        })
    }

    private fun addToList2(title: String, image: String) {
        titleList2.add(title)
        imageList2.add(image)
    }

    private fun setUpRecyclerView() {
        upcomingRecyclerView?.layoutManager = gridLayoutManager
        upcomingRecyclerView?.adapter = context?.let { MovieAdapter(it, titleList2, imageList2) }
    }

    private fun getUpcomingMovies(page: Int, limit: Int) {
        val upcomingApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUpcoming::class.java)
        val data = upcomingApi.getUpcomingMovies(page, limit)
        data.enqueue(object : Callback<MovieJson?> {
            override fun onResponse(call: Call<MovieJson?>, response: Response<MovieJson?>) {
                if (response.isSuccessful && response.body() != null) {
                    progressBar?.visibility = View.GONE
                    val responseBody = response.body()!!
                    for (movies in responseBody.results) {
                        Log.i("UpcomingFragment", "Result = $movies")
                        addToList2(movies.title, "http://image.tmdb.org/t/p/w500${movies.posterPath}")
                    }
                    setUpRecyclerView()
                }
            }

            override fun onFailure(call: Call<MovieJson?>, t: Throwable) {
                Log.e("UpcomingFragment", t.toString())
            }
        })

    }


}