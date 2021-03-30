package com.iyanuoluwa.imovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iyanuoluwa.imovie.adapter.MovieAdapter
import com.iyanuoluwa.imovie.data.ApiUpcoming
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpComing : Fragment() {

    private var upcomingRecyclerView: RecyclerView? =null
    private var gridLayoutManager: GridLayoutManager? = null
    private var titleList2 = mutableListOf<String>()
    private var imageList2 = mutableListOf<String>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_up_coming, container, false)
        upcomingRecyclerView = view.findViewById(R.id.upcoming_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUpcomingMovies()
    }

    private fun addToList2(title: String, image: String) {
        titleList2.add(title)
        imageList2.add(image)
    }

    private fun setUpRecyclerView() {
        upcomingRecyclerView?.layoutManager = gridLayoutManager
        upcomingRecyclerView?.adapter = context?.let { MovieAdapter(it, titleList2, imageList2) }
    }

    private fun getUpcomingMovies() {
        val upcomingApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUpcoming::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response2 = upcomingApi.getUpcomingMovies()
                for (movies2 in response2.results) {
                    addToList2(movies2.originalTitle, "http://image.tmdb.org/t/p/w500${movies2.posterPath}")
                }
                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                }
            } catch (e: Exception) {
                Log.e("UpcomingFragment", e.toString())
            }
        }

    }


}