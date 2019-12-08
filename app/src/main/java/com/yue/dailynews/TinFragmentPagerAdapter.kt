package com.yue.dailynews

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yue.dailynews.common.ContainerFragment


class TinFragmentPagerAdapter: FragmentPagerAdapter{
    companion object{
        const val FRAGMENT_NUMBER = 3
    }
    private val fragments: ArrayList<Fragment> = ArrayList()

    constructor(fm: FragmentManager) : super(fm) {
        for (i in 0 until FRAGMENT_NUMBER) {
            fragments.add(ContainerFragment.newInstance(i))
        }
    }
    //https://blog.csdn.net/u012516952/article/details/78835703

    override fun getItem(position: Int): Fragment {
//        return fragments[position]
        if (position<0 ||position >= FRAGMENT_NUMBER){
            throw IndexOutOfBoundsException("Out of Boundary")
        }
        return fragments.get(position)
    }

    override fun getCount(): Int {
       return FRAGMENT_NUMBER;
    }
}
