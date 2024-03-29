package com.yue.dailynews.save.detail

import android.app.FragmentManager
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.yue.dailynews.MainActivity
import com.yue.dailynews.R
import com.yue.dailynews.common.BaseViewModel
import com.yue.dailynews.common.TinBasicActivity
import com.yue.dailynews.common.TinFragmentManager
import com.yue.dailynews.save.SavedNewsFragment

class goBackViewModel: BaseViewModel<goBackViewModel.Companion.goBackViewHolder> {
    var fragment: SavedNewsDetailedFragment
    var mtinFragmentManager: TinFragmentManager
    constructor(mtinFragmentManager: TinFragmentManager,fragment: SavedNewsDetailedFragment):super(R.layout.go_back){
        this.mtinFragmentManager = mtinFragmentManager
        this.fragment = fragment
    }

    override fun createItemViewHolder(view: View): goBackViewHolder{
        return  goBackViewHolder(view)
    }

    override fun bindViewHolder(holder:  goBackViewHolder) {
        holder.goBack.setOnClickListener{view->
            mtinFragmentManager.doFragmentTransaction(SavedNewsFragment.newInstance())
//            var fm  = fragment.activity!!.getSupportFragmentManager()
//            fm.popBackStack ()
        }
    }



    companion object {
        class  goBackViewHolder: RecyclerView.ViewHolder{
            var goBack: Button
            constructor(itemView: View):super(itemView){
                goBack = itemView.findViewById(R.id.go_back)
            }
        }
    }
}

