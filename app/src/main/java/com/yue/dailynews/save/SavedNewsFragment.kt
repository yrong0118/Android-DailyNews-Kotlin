package com.yue.dailynews.save


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.util.MapUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.yue.dailynews.R
import com.yue.dailynews.common.TinBasicFragment
import com.yue.dailynews.common.TinFragmentManager
import com.yue.dailynews.common.Util
import com.yue.dailynews.common.Util.Companion.mapLike
import com.yue.dailynews.retrofit.response.LikeOrDisLikeUser
import com.yue.dailynews.retrofit.response.News
import com.yue.dailynews.save.detail.SavedNewsDetailedFragment
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.title_layout.*
import org.jetbrains.anko.find
import java.util.*
import kotlin.collections.ArrayList

class SavedNewsFragment : TinBasicFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): SavedNewsFragment {
            var args = Bundle()
            var fragment = SavedNewsFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    private lateinit var emptyState: TextView
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var firebaseDatabase:FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Util.setAppLocale(Util.language,resources)
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_saved_news, container, false)
        emptyState = view.findViewById(R.id.empty_state)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        database()
        return view
    }

    override fun onResume() {
        super.onResume()
        database()
    }
//    override  fun onBackPressed(){
//
//    }

    fun database(){
        //获取数据库实例
        firebaseDatabase  = FirebaseDatabase.getInstance()
        //获取Reference  "message"为存储值的key

        firebaseAuth = FirebaseAuth.getInstance()
        Log.d("login", "onCreate called")
        val operList = mutableListOf<LikeOrDisLikeUser>()
        val opRef : DatabaseReference = firebaseDatabase.getReference("dailyNews/Opeartions/${firebaseAuth.currentUser?.uid}")
        val newsList = mutableListOf<News>()

        val newsRef : DatabaseReference = firebaseDatabase.getReference("dailyNews/News")
        newsRef.addValueEventListener(object: ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@SavedNewsFragment.activity,
                    "Failed to retrieve savedNews:$error",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                newsList.clear()
                dataSnapshot.children.forEach { child ->
                    val news = child.getValue(News::class.java)
                    if (news != null) {
                        newsList.add(news)
                    }
                }
            }
        })

        opRef.addValueEventListener(object: ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@SavedNewsFragment.activity,
                    "Failed to retrieve operation:$error",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val showList = mutableListOf<News>()
                mapLike.clear()
                dataSnapshot.children.forEach { child ->
                    val oper = child.getValue(LikeOrDisLikeUser::class.java)
                    if (oper != null) {
                        operList.add(oper)
                        mapLike.put(oper.title,oper.isLike)
                    }
                }
                for (cur in newsList){
                    if (mapLike.containsKey(cur.title) && mapLike.get(cur.title)!!){
                        showList.add(cur)
                    }
                }
                if (showList.size != 0) {
                    emptyState.visibility = View.GONE
                }else {
                    emptyState.visibility = View.VISIBLE
                }

                recyclerView.adapter=SavedNewsAdapter(showList,tinFragmentManager)
            }
        })


    }

}


