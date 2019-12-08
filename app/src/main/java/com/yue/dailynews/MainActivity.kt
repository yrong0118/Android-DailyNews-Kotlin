package com.yue.dailynews

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.internal.NavigationMenuView
import com.yue.dailynews.common.ContainerFragment
import com.yue.dailynews.common.TinBasicActivity
import com.yue.dailynews.common.TinBasicFragment
import com.yue.dailynews.common.Util
import com.yue.dailynews.save.detail.SavedNewsDetailedFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : TinBasicActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var adapter: TinFragmentPagerAdapter

    override protected fun onCreate(savedInstanceState: Bundle?) {

        Util.setAppLocale(Util.language,resources)

        super.onCreate(savedInstanceState)
        viewPager = findViewById(R.id.viewpager)
        adapter = TinFragmentPagerAdapter(getSupportFragmentManager())
        viewPager.setAdapter(adapter)
        viewPager.setOffscreenPageLimit(TinFragmentPagerAdapter.FRAGMENT_NUMBER)

        bottomBar = findViewById(R.id.bottom_navigation)
        bottomBar.setOnNavigationItemSelectedListener { item ->
            viewPager.setCurrentItem(ContainerFragment.getPositionById(item.itemId))
            true
        }
    }


    private fun getCurrentChildFragmentManager():FragmentManager{
        return adapter.getItem(viewPager.getCurrentItem()).getChildFragmentManager()
    }


    override fun doFragmentTransaction(basicFragment: TinBasicFragment) {
        var fragmentTransaction = getCurrentChildFragmentManager().beginTransaction()
        fragmentTransaction.replace(
            R.id.child_fragment_container,
            basicFragment,
            basicFragment.getFragmentTag()
        ).addToBackStack(null).commit()
    }

    override fun showSnackBar(message:String){

    }

    protected override fun onSaveInstanceState(outState:Bundle){
        super.onSaveInstanceState(outState);
    }


    override fun onBackPressed(){
        var fragmentManager= getCurrentChildFragmentManager()
        if(fragmentManager.backStackEntryCount>0){
            fragmentManager.popBackStack()
        }else {
            super.onBackPressed()
        }
    }


        override fun getLayout(): Int {
        return R.layout.activity_main
    }


}

