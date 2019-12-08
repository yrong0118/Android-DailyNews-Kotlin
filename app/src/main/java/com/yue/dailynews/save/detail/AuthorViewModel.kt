package com.yue.dailynews.save.detail


import android.view.View
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.yue.dailynews.R
import com.yue.dailynews.common.BaseViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.text.ParseException


class AuthorViewModel :BaseViewModel<AuthorViewModel.Companion.AuthorViewModelHolder>{
    private val author :String
    private val timeStamp : String
//    init {
//        super(R.layout.author_layout)
//        this.author = author
//        this.timeStamp = timeStamp
//    }
//    constructor(author: String,timestamp: String){
//
//    }

    constructor(author: String,timeStamp: String):super(R.layout.author_layout){
        this.author = author
        this.timeStamp = timeStamp
    }


    override fun createItemViewHolder(view: View): AuthorViewModelHolder {
        return AuthorViewModelHolder(view)
    }

    override fun bindViewHolder(holder: AuthorViewModelHolder) {
        holder.author.setText(author)
        var simpleDateFormat:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
            Locale.getDefault()
        )
        val date:Date
        var formatTime:String = ""
        try {
            date = simpleDateFormat.parse(timeStamp)
            simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            formatTime = simpleDateFormat.format(date)
        }catch (e: ParseException){
            e.printStackTrace()
        }
        holder.timeStamp.setText(formatTime)
    }

    companion object{
        class AuthorViewModelHolder: RecyclerView.ViewHolder {
            var author: TextView
            var timeStamp: TextView

            constructor(itemView: View):super(itemView){
                author = itemView.findViewById(com.yue.dailynews.R.id.author)
                timeStamp = itemView.findViewById(com.yue.dailynews.R.id.time_stamp)
            }

        }
    }

}

