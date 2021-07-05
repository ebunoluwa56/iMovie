package com.iyanuoluwa.imovie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iyanuoluwa.imovie.ui.common.NaughtyPager
import com.iyanuoluwa.imovie.ui.main.NowPlaying
import com.iyanuoluwa.imovie.ui.main.Popular
import com.iyanuoluwa.imovie.ui.main.TopRated
import com.iyanuoluwa.imovie.ui.main.UpComing

class MainActivity : AppCompatActivity() {

    private val upcomingFragment = UpComing()
    private val nowPlayingFragment = NowPlaying()
    private val topRatedFragment = TopRated()
    private val popularFragment = Popular()


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val viewPager = findViewById<NaughtyPager>(R.id.view_pager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragmentToList(nowPlayingFragment)
        viewPagerAdapter.addFragmentToList(upcomingFragment)
        viewPagerAdapter.addFragmentToList(popularFragment)
        viewPagerAdapter.addFragmentToList(topRatedFragment)

        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 3

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
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when(position) {
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

        private val fragmentList : MutableList<Fragment>  = ArrayList()

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