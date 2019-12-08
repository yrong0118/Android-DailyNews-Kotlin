package com.yue.dailynews.tin



import android.util.Log
import android.widget.ImageView
import android.widget.TextView

import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState
import com.mindorks.placeholderview.annotations.swipe.SwipeIn
import com.mindorks.placeholderview.annotations.swipe.SwipeInState
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState
import com.yue.dailynews.common.Util
import com.squareup.picasso.Picasso
import com.yue.dailynews.R
import com.yue.dailynews.retrofit.response.News


@Layout(R.layout.tin_news_card)
class TinNewsCard(private val news: News, private val swipeView: SwipePlaceHolderView, private val onSwipeListener: OnSwipeListener) {

    @View(R.id.news_image)
    private lateinit var image: ImageView

    @View(R.id.news_title)
    private lateinit var newsTitle: TextView

    @View(R.id.news_description)
    private lateinit var newsDescription: TextView
    @Resolve
    private fun onResolved() {
        if (Util.isStringEmpty(news.image)) {
            image.setImageResource(R.drawable.no_image_available)
        } else {
            Picasso.get().load(news.image).into(image)
        }
        newsTitle.text = news.title
        newsDescription.text = news.description
    }

    @SwipeOut
    private fun onSwipedOut() {
        Log.d("EVENT", "onSwipedOut")
        swipeView.addView(this)
        onSwipeListener.onDisLike(news,false)
    }

    @SwipeCancelState
    private fun onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState")
    }

    @SwipeIn
    private fun onSwipeIn() {
        Log.d("EVENT", "onSwipedIn")
        onSwipeListener.onLike(news,true)

    }

    @SwipeInState
    private fun onSwipeInState() {
        Log.d("EVENT", "onSwipeInState")
    }

    @SwipeOutState
    private fun onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState")
    }


    interface OnSwipeListener {
        fun onLike(news: News,isLike:Boolean)
        fun onDisLike(news: News,isLike: Boolean)

    }


}


    //https://medium.com/@agrawalsuneet/companion-object-in-kotlin-5251e03d6423
