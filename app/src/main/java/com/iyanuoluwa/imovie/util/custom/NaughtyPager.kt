package com.iyanuoluwa.imovie.util.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by mofeejegi on 4/17/21.
 *
 * A {@link ViewPager} who's paging abilities can be disabled.
 */
class NaughtyPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    /**
     * @return true if paging is enabled on this pager, false otherwise.
     */
    var isPagingEnabled = false // ref to if this has paging enabled or not

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // check if paging is enabled or not
        return isPagingEnabled && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isPagingEnabled && super.onTouchEvent(ev)
    }
}