package com.yue.dailynews.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yue.dailynews.R

abstract class TinBasicActivity : AppCompatActivity(),TinFragmentManager  {

    companion object{
        protected const val BUNDLE = "bundle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }
    override fun startActivityWithBundle(clazz: Class<Any>, isFinished: Boolean, bundle: Bundle) {
       var intent = Intent(this,clazz)
        intent.putExtra(BUNDLE,bundle)
        this.startActivity(intent)
        if(isFinished){
            finish()
        }

    }
    protected abstract fun getLayout():Int

    override fun showSnackBar(message: String) {
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
