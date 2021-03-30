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
import com.iyanuoluwa.imovie.data.ApiPopular
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Popular : Fragment() {

    private var popularRecyclerView: RecyclerView? =null
    private var gridLayoutManager: GridLayoutManager? = null
    private var titleList3 = mutableListOf<String>()
    private var imageList3 = mutableListOf<String>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        popularRecyclerView = view.findViewById(R.id.popular_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        return view
    }

    private fun addToList(title : String, image: String) {
        titleList3.add(title)
        imageList3.add(image)
    }

    private fun setUpRecyclerView() {
        popularRecyclerView?.layoutManager = gridLayoutManager
        popularRecyclerView?.adapter = context?.let { MovieAdapter(it, titleList3, imageList3) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPopularMovies()
    }

    private fun getPopularMovies() {
        val apiPopular = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiPopular::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response3 = apiPopular.getPopularMovies()
            try {
                for (movies3 in response3.results) {
                    Log.i("PopularFragment", "Result = $movies3")
                    addToList(movies3.title, "http://image.tmdb.org/t/p/w500${movies3.posterPath}")
                }
                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                }
            }catch (e: Exception) {
                Log.e("PopularFragment", e.toString())
            }
        }
    }

}