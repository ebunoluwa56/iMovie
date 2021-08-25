package com.iyanuoluwa.imovie.ui.main.fragments

import android.os.Bundle
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
import com.google.android.material.snackbar.Snackbar
import com.iyanuoluwa.imovie.MovieApplication
import com.iyanuoluwa.imovie.R
import com.iyanuoluwa.imovie.data.model.Category
import com.iyanuoluwa.imovie.data.model.Result
import com.iyanuoluwa.imovie.ui.main.MovieAdapter
import com.iyanuoluwa.imovie.ui.main.MovieViewModel
import com.iyanuoluwa.imovie.ui.main.ViewModelFactory
import com.iyanuoluwa.imovie.util.Resource

class NowPlaying : Fragment() {

    private var textView: TextView? = null
    private var playingRecyclerView: RecyclerView? =null
    private var gridLayoutManager: GridLayoutManager? = null
    private var nestedScrollView: NestedScrollView? = null
    private var progressBar: ProgressBar? = null
    private var page: Int = 1
    private var limit: Int = 20

    private lateinit var adapter : MovieAdapter

    private val movieViewModel: MovieViewModel by viewModels {
        ViewModelFactory((requireActivity().application as MovieApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        getMovies(page, limit, Category.NOW_PLAYING)

        nestedScrollView?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == (v?.getChildAt(0)?.measuredHeight)?.minus(v.measuredHeight)) {
                page++
                getMovies(page, limit, Category.NOW_PLAYING)
            }
        })
    }

    private fun initViews(view: View) {
        textView = view.findViewById(R.id.playing)
        playingRecyclerView = view.findViewById(R.id.playing_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        nestedScrollView = view.findViewById(R.id.scroll_view_now)
        progressBar = view.findViewById(R.id.progress_bar_now)

        adapter = MovieAdapter(requireContext(), mutableListOf())

        playingRecyclerView?.layoutManager = gridLayoutManager
        playingRecyclerView?.adapter = adapter
    }

    private fun getMovies(page: Int, limit: Int, category: Category) {
        movieViewModel.getMovies(page, limit, category).observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is Resource.Loading -> progressBar?.visibility = View.VISIBLE
                    is Resource.Success -> {
                        progressBar?.visibility = View.GONE

                        if (page == 1) {
                            // Insert movies locally in the db only on the first page
                            movieViewModel.insertMovies(it.data)
                            // refresh data on page 1
                            adapter.movies = it.data as MutableList<Result>
                        } else {
                            // add data on subsequent pages
                            adapter.movies.addAll(it.data)
                        }

                        playingRecyclerView?.adapter?.notifyDataSetChanged()
                    }

                    is Resource.Failure -> {
                        progressBar?.visibility = View.GONE
                        Snackbar.make(textView!!, it.throwable.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        )
    }
}
