package com.yue.dailynews.common

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yue.dailynews.R
import com.yue.dailynews.profile.ProfileFragment

import com.yue.dailynews.save.SavedNewsFragment
import com.yue.dailynews.tin.TinGalleryFragment


class ContainerFragment : TinBasicFragment(){
    // TODO: Rename and change types of parameters

    //有什么区别这两个pageIndex的定义
//    private var pageIndex = Int
    private var pageIndex: Int = 0
    private lateinit var initFragment:Fragment

    companion object {

        val HOME_PAGE = 0
        val HOME_PAGE_TAG = "home_page"
        val SAVE_PAGE = 1
        val SAVE_PAGE_TAG = "save_page"
        val PROFILE_PAGE = 2
        val PROFILE_PAGE_TAG = "profile_page"

        @JvmStatic
        fun newInstance(pageIndex: Int):ContainerFragment{
            var containerFragment = ContainerFragment()
            containerFragment.pageIndex = pageIndex
            containerFragment.initFragment = createInitFragmentByIndex(pageIndex)
            return containerFragment
        }

        fun createInitFragmentByIndex(pageIndex: Int):Fragment{
            when (pageIndex) {
                HOME_PAGE -> {
//                    return SavedNewsFragment.newInstance()
                    return TinGalleryFragment.newInstance()
                }
                SAVE_PAGE -> {
                    return SavedNewsFragment.newInstance()
                }
                PROFILE_PAGE -> {
                    return ProfileFragment.newInstance()
//                    return TinProfileFragment.newInstance()
                }
                else -> throw IndexOutOfBoundsException() as Throwable
            }

        }


         fun getPositionById(id:Int):Int{
            when(id){
                com.yue.dailynews.R.id.action_tin -> {
                    return HOME_PAGE
                }
                com.yue.dailynews.R.id.action_save -> {
                    return SAVE_PAGE
                }
                com.yue.dailynews.R.id.action_profile -> {
                    return PROFILE_PAGE
                }
                else -> throw IndexOutOfBoundsException()
            }

        }
        fun getCurrentTag(position: Int): String? {
            when (position) {
                HOME_PAGE -> return HOME_PAGE_TAG
                SAVE_PAGE -> return SAVE_PAGE_TAG
                PROFILE_PAGE -> return PROFILE_PAGE_TAG
                else -> return null
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.child_fragment_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (initFragment != null && !initFragment.isAdded()) {
            getChildFragmentManager().beginTransaction().replace(R.id.child_fragment_container,initFragment,
                getCurrentTag(pageIndex)).commit()

        }
    }

}
