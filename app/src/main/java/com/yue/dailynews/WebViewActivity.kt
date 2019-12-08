package com.yue.dailynews

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.wifi.hotspot2.omadm.PpsMoParser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.webkit.WebChromeClient
import android.widget.PopupMenu
import android.widget.Toast
import com.yue.dailynews.common.TinBasicActivity
import com.yue.dailynews.common.TinBasicFragment

import java.util.*


class WebViewActivity : TinBasicActivity(),PopupMenu.OnMenuItemClickListener {
    override fun doFragmentTransaction(basicFragment: TinBasicFragment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var back: ImageView
    private lateinit var more: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var webView: WebView
    private lateinit var url: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        back = findViewById(R.id.back)
        more = findViewById(R.id.more)
        progressBar = findViewById(R.id.progress_bar)

        back.setOnClickListener {
            onBackPressed()

        }
        url = "https://www.google.com"
        webView = findViewById(R.id.web_view)
        webView.getSettings().setJavaScriptEnabled(true)
        webView.webChromeClient = object : WebChromeClient() {}
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE)
            }
        }

        webView.loadUrl(url)
        more.setOnClickListener {

        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_web_view;
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
