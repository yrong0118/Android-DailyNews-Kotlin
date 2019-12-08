package com.yue.dailynews.common

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.view.View


 open abstract class BaseViewModel<V :  RecyclerView.ViewHolder> {
     private var itemResourceId:Int

     constructor(itemResourceId: Int){
         this.itemResourceId = itemResourceId
     }

     fun createViewHolder(parent: ViewGroup): V {
        val view = LayoutInflater.from(parent.context).inflate(itemResourceId, parent, false)
        return createItemViewHolder(view)
    }

   abstract fun createItemViewHolder(view: View):V

    abstract fun  bindViewHolder(holder : V)

    fun getViewType() : Int {
        return itemResourceId

    }
}