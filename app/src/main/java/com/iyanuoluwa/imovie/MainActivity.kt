package com.iyanuoluwa.imovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val upcomingFragment = UpComing()
    private val nowPlayingFragment = NowPlaying()
    private val topRatedFragment = TopRated()
    private val popularFragment = Popular()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(nowPlayingFragment)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nowPlaying -> replaceFragment(nowPlayingFragment)
                R.id.upcoming -> replaceFragment(upcomingFragment)
                R.id.topRated -> replaceFragment(topRatedFragment)
                R.id.popular -> replaceFragment(popularFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}