package com.yue.dailynews.save.detail

import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.placeholderview.annotations.Layout
import com.yue.dailynews.R
import com.yue.dailynews.common.BaseViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TitleViewModel: BaseViewModel<TitleViewModel.Companion.TitleViewModelHolder> {

   private val title:String
    constructor(title:String):super(R.layout.title_layout){
        this.title = title
    }
   override fun createItemViewHolder(view:View):TitleViewModelHolder{
        return TitleViewModelHolder(view)
    }

    override fun bindViewHolder(holder: TitleViewModelHolder) {
        holder.title.setText(title)
    }

    companion object {
        class TitleViewModelHolder: RecyclerView.ViewHolder {

            var title: TextView
            constructor(itemView: View):super(itemView){
                title = itemView.findViewById(com.yue.dailynews.R.id.title)
            }
        }
    }
}