package com.iyanuoluwa.imovie.ui.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iyanuoluwa.imovie.R
import com.iyanuoluwa.imovie.data.model.Category
import com.iyanuoluwa.imovie.ui.common.NaughtyPager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val viewPager = findViewById<NaughtyPager>(R.id.view_pager)

        val upcomingFragment = MovieFragment.newInstance(Category.UPCOMING)
        val nowPlayingFragment = MovieFragment.newInstance(Category.NOW_PLAYING)
        val topRatedFragment = MovieFragment.newInstance(Category.TOP_RATED)
        val popularFragment = MovieFragment.newInstance(Category.POPULAR)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragmentToList(nowPlayingFragment)
        viewPagerAdapter.addFragmentToList(upcomingFragment)
        viewPagerAdapter.addFragmentToList(popularFragment)
        viewPagerAdapter.addFragmentToList(topRatedFragment)

        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 3

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.navigationBarColor = this.resources.getColor(com.google.android.material.R.color.design_default_color_primary_variant)
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nowPlaying -> viewPager.setCurrentItem(0, false)
                R.id.upcoming -> viewPager.setCurrentItem(1, false)
                R.id.popular -> viewPager.setCurrentItem(2, false)
                R.id.topRated -> viewPager.setCurrentItem(3, false)
            }
            false
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNav.menu.findItem(R.id.nowPlaying).isChecked = true
                    1 -> bottomNav.menu.findItem(R.id.upcoming).isChecked = true
                    2 -> bottomNav.menu.findItem(R.id.popular).isChecked = true
                    3 -> bottomNav.menu.findItem(R.id.topRated).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragmentList: MutableList<Fragment> = ArrayList()

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragmentToList(fragment: Fragment) {
            fragmentList.add(fragment)
        }


        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

    }


}