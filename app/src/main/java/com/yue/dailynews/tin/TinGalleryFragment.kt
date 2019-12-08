package com.yue.dailynews.tin


import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder

import com.yue.dailynews.R
import com.yue.dailynews.common.ContainerFragment
import com.yue.dailynews.common.TinBasicFragment
import com.yue.dailynews.common.Util
import com.yue.dailynews.common.Util.Companion.mapLike
import com.yue.dailynews.retrofit.NewsRequestApi
import com.yue.dailynews.retrofit.RetrofitClient
import com.yue.dailynews.retrofit.response.BaseResponse
import com.yue.dailynews.retrofit.response.LikeOrDisLikeUser
import com.yue.dailynews.retrofit.response.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.flowable.FlowableBlockingSubscribe.subscribe
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.fragment_tin_gallery.*
import org.jetbrains.anko.doAsync
import retrofit2.create

/**
 * A simple [Fragment] subclass.
 */
class TinGalleryFragment : TinBasicFragment(), TinNewsCard.OnSwipeListener {
    override fun onDisLike(news: News, isLike: Boolean) {
        saveFavoriteNews(news, isLike)
    }

    override fun onLike(news: News, isLike: Boolean) {
        saveFavoriteNews(news, isLike)
    }

    private lateinit var mSwipeView: SwipePlaceHolderView
    private lateinit var rejectBtn: ImageButton
    private lateinit var acceptBtn: ImageButton
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        @JvmStatic
        fun newInstance(): TinGalleryFragment {
            var args = Bundle()
            var fragment = TinGalleryFragment()
            fragment.setArguments(args)
            return fragment
        }

        var country: String = "us"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Util.setAppLocale(Util.language, resources)
        firebaseDatabase = FirebaseDatabase.getInstance()

        firebaseAuth = FirebaseAuth.getInstance()

        Log.d("login", "onCreate called")

        val view = inflater.inflate(R.layout.fragment_tin_gallery, container, false)
        mSwipeView = view.findViewById(R.id.swipeView)

        val opRef: DatabaseReference =
            firebaseDatabase.getReference("dailyNews/Opeartions/${firebaseAuth.currentUser?.uid}")

        opRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@TinGalleryFragment.activity,
                    "Failed to retrieve operation:$error",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val showList = mutableListOf<News>()
                dataSnapshot.children.forEach { child ->
                    val oper = child.getValue(LikeOrDisLikeUser::class.java)
                    if (oper != null) {
                        mapLike.put(oper.title, oper.isLike)
                    }

//                    ************************


                    mSwipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                        .setDisplayViewCount(3)
                        .setSwipeDecor(
                            SwipeDecor()
                                .setPaddingTop(20)//20
                                .setRelativeScale(0.05f)//0.01f
                                .setSwipeInMsgLayoutId(R.layout.tin_news_swipe_in_msg_view)
                                .setSwipeOutMsgLayoutId(R.layout.tin_news_swipe_out_msg_view)
                        )
                    rejectBtn = view.findViewById(R.id.rejectBtn)
                    acceptBtn = view.findViewById(R.id.acceptBtn)
                    rejectBtn.setOnClickListener {
                        mSwipeView.doSwipe(false)
                    }
                    acceptBtn.setOnClickListener {
                        mSwipeView.doSwipe(true)
                    }
                    getData()
//                    ************************
                }
            }
        })

        return view

//        doAsync {
//            activity!!.runOnUiThread(){}
//        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    override fun onPause() {
        super.onPause()
        getData()
    }


    fun getData() {
        var newsRequestApi = RetrofitClient.instance.create(NewsRequestApi::class.java)
        val API = getString(R.string.newsApi)
        newsRequestApi.getNewsByCountry(country, API)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { baseResponse -> baseResponse != null && baseResponse.articles != null }
            .subscribe(
                { baseResponse -> showNewsCard(baseResponse.articles) },
                { throwable ->
                    Toast.makeText(this.context, "News API Failed", Toast.LENGTH_SHORT).show()
                    throwable.printStackTrace()
                }
            )
    }


    fun saveFavoriteNews(news: News, isLike: Boolean) {
        var referenceNews = firebaseDatabase.getReference("dailyNews/News/${news.title}")
        val email: String = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val newsObject =
            News((news.author) ?: "", news.title, news.description, news.url, news.image, news.time)
        referenceNews.setValue(newsObject)
        var referenceUsers =
            firebaseDatabase.getReference("dailyNews/Opeartions/${firebaseAuth.currentUser?.uid}/${news.title}")
        val OperationObject =
            LikeOrDisLikeUser(news.title, isLike)
        referenceUsers.setValue(OperationObject)
    }

    fun showNewsCard(newsList: List<News>) {
        val opRef: DatabaseReference =
            firebaseDatabase.getReference("dailyNews/Opeartions/${firebaseAuth.currentUser?.uid}")

        opRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@TinGalleryFragment.activity,
                    "Failed to retrieve operation:$error",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mapLike.clear()
                dataSnapshot.children.forEach { child ->
                    val oper = child.getValue(LikeOrDisLikeUser::class.java)
                    if (oper != null) {
                        mapLike.put(oper.title, oper.isLike)
                    }
                }

                for (news: News in newsList) {
                    if (!mapLike.containsKey(news.title)) {
                        var tinNewsCard: TinNewsCard =
                            TinNewsCard(news, mSwipeView, this@TinGalleryFragment)
                        swipeView.addView(tinNewsCard)
                    }
                }
            }
        })
    }
}
