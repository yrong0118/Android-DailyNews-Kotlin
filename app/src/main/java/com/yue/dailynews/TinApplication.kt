package com.yue.dailynews

import android.app.Application
import com.facebook.stetho.Stetho

class TinApplication: Application(){
    override fun onCreate(){
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}