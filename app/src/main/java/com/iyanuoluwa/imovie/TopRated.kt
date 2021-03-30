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
import com.iyanuoluwa.imovie.data.ApiTopRated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TopRated : Fragment() {

    private var topRatedRecyclerView: RecyclerView? =null
    private var gridLayoutManager: GridLayoutManager? = null
    private var titleList = mutableListOf<String>()
    private var imageList = mutableListOf<String>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_top_rated, container, false)
        topRatedRecyclerView = view.findViewById(R.id.top_rated_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
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
        getTopRatedMovies()
    }

    private fun getTopRatedMovies() {
        val apiTopRated = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiTopRated::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = apiTopRated.getTopRatedMovies()
            try {
                for (movies in response.results) {
                    Log.i("TopRatedFragment", "Result = $movies")
                    addToList(movies.title, "http://image.tmdb.org/t/p/w500${movies.posterPath}")
                }
                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                }
            } catch (e: Exception) {
                Log.e("TopRatedFragment", e.toString())
            }
        }
    }

}