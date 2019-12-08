package com.yue.dailynews.save.detail

import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.placeholderview.annotations.Layout
import com.yue.dailynews.R
import com.yue.dailynews.common.BaseViewModel
import kotlinx.android.synthetic.main.saved_news_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DescriptionViewModel : BaseViewModel<DescriptionViewModel.Companion.DescriptionViewHolder> {
    private val description:String

    constructor(description:String):super(R.layout.description_layout){
        this.description = description
    }

   override fun createItemViewHolder(view:View): DescriptionViewHolder{
        return  DescriptionViewHolder(view)
    }

    override fun bindViewHolder(holder:  DescriptionViewHolder) {
        holder.description.setText(description)
    }



    companion object {
        class  DescriptionViewHolder: RecyclerView.ViewHolder{

            var description: TextView
            constructor(itemView: View):super(itemView){
                description = itemView.findViewById(com.yue.dailynews.R.id.description)
            }
        }
    }
}