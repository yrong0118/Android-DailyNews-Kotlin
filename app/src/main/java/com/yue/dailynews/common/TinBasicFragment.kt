package com.yue.dailynews.common

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

open class TinBasicFragment : Fragment() {
    // TODO: Rename and change types of parameters
    protected lateinit var tinFragmentManager: TinFragmentManager
    private  val uuid: String = UUID.randomUUID().toString()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        tinFragmentManager = context as TinFragmentManager
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.yue.dailynews.R.layout.fragment_tin_basic, container, false)
    }

    fun getFragmentTag():String {
        return this.javaClass.name + uuid
    }


}
