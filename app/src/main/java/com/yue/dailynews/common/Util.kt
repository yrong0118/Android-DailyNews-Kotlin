package com.yue.dailynews.common

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.yue.dailynews.retrofit.response.News
import java.util.*
import kotlin.collections.HashMap

class Util(){
    companion object{
        fun isStringEmpty(str:String):Boolean{
            return (str == null || str.length == 0)
        }
        var language = "us"
        fun setAppLocale(localeCode: String,res:Resources){
            var dm : DisplayMetrics
            dm = res.displayMetrics
            var conf : Configuration
            conf = Configuration()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                conf.setLocale(Locale(localeCode.toLowerCase()))
            }else {
                conf.locale = Locale(localeCode.toLowerCase())
            }
            res.updateConfiguration(conf,dm)
        }
        var mapLike = HashMap<String,Boolean>()

    }
}