package com.yue.dailynews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class TinNoSwipeViewPager: ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onInterceptTouchEvent(event: MotionEvent):Boolean{
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }
}

