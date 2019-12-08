package com.yue.dailynews.common

import android.os.Bundle



interface TinFragmentManager {
    fun doFragmentTransaction(basicFragment: TinBasicFragment)
    fun startActivityWithBundle(clazz: Class<Any>, isFinished: Boolean, bundle: Bundle)
    fun showSnackBar(message: String)

}