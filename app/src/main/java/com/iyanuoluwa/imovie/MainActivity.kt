package com.iyanuoluwa.imovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val upcomingFragment = UpComing()
    private val nowPlayingFragment = NowPlaying()
    private val topRatedFragment = TopRated()
    private val popularFragment = Popular()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(nowPlayingFragment)
        viewPagerAdapter.addFragment(upcomingFragment)
        viewPagerAdapter.addFragment(popularFragment)
        viewPagerAdapter.addFragment(topRatedFragment)
        viewPager.adapter = viewPagerAdapter
    }






    class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragmentList : MutableList<Fragment> = ArrayList()

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

    }


}