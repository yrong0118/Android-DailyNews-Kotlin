package com.yue.dailynews.save.detail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yue.dailynews.R
import com.yue.dailynews.common.BaseViewModel

class ImageViewModel: BaseViewModel<ImageViewModel.Companion.ImageViewModelViewHolder>{
    private val url: String
    constructor(url: String):super(R.layout.image_layout){
        this.url =url
    }
    override fun createItemViewHolder(view: View):ImageViewModelViewHolder{
        return ImageViewModelViewHolder(view)
    }

    override fun bindViewHolder(holder: ImageViewModelViewHolder) {
        Picasso.get().load(url).into(holder.image)
    }

    companion object {
        class ImageViewModelViewHolder: RecyclerView.ViewHolder {

            var image:ImageView
            constructor(itemView: View):super(itemView) {
                image = itemView.findViewById(com.yue.dailynews.R.id.imageI)
            }
        }
    }
}