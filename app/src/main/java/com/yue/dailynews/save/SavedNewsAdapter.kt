package com.yue.dailynews.save

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yue.dailynews.R
import com.yue.dailynews.common.TinBasicFragment
import com.yue.dailynews.common.TinFragmentManager
import com.yue.dailynews.common.Util
import com.yue.dailynews.retrofit.response.News
import com.yue.dailynews.save.detail.SavedNewsDetailedFragment
import com.yue.dailynews.tin.TinGalleryFragment


class SavedNewsAdapter(val newsList: List<News>,val mtinFragmentManager: TinFragmentManager): RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>(){
//    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val ICON_ARRAY = intArrayOf(
        R.drawable.a_news_icon,
        R.drawable.g_news_icon,
        R.drawable.c_news_icon,
        R.drawable.y_news_icon,
        R.drawable.m_news_icon
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_news_item, parent, false)


        return SavedNewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }


    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val currentNews = newsList[position]
        if (!Util.isStringEmpty(currentNews.title)){
            holder.title.setText(currentNews.title)
        }
        holder.description.text =currentNews.description
        holder.icon.setImageResource(getDrawable())
        holder.itemView.setOnClickListener{view->
          mtinFragmentManager.doFragmentTransaction(SavedNewsDetailedFragment.newInstance(currentNews))
        }

    }
    private fun getDrawable(): Int {
        return ICON_ARRAY[(Math.random() * 5).toInt()]
    }

    class SavedNewsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title:TextView
        val description:TextView
        val icon:ImageView
        init{
            title = view.findViewById(R.id.titleSaved)
            description = view.findViewById(R.id.desciption)
            icon = view.findViewById(R.id.image)}
    }

}







