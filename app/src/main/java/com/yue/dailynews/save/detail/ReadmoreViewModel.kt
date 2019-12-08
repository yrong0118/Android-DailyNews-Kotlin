package com.yue.dailynews.save.detail

import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.placeholderview.annotations.Layout
import com.yue.dailynews.common.BaseViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReadmoreViewModel(var url:String) : BaseViewModel<ReadmoreViewModel.Companion.ReadmoreViewModelHolder>(0) {
   override fun createItemViewHolder(view:View):ReadmoreViewModelHolder {
        return ReadmoreViewModelHolder (view)
    }

    override fun bindViewHolder(holder: ReadmoreViewModelHolder ) {
        holder.readMore.setText(url)
    }

    companion object {
        class ReadmoreViewModelHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

            var readMore: TextView

            init {
                readMore = itemView.findViewById(com.yue.dailynews.R.id.readmore)
            }
        }
    }
}