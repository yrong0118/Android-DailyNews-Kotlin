package com.yue.dailynews.common

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class ViewModelAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var viewModels: MutableList<BaseViewModel<RecyclerView.ViewHolder>> = ArrayList<BaseViewModel<RecyclerView.ViewHolder>>()
    private var viewTypeMap: SparseArrayCompat<BaseViewModel<RecyclerView.ViewHolder>> = SparseArrayCompat()

    fun addViewModels(viewModels: Collection< out BaseViewModel<RecyclerView.ViewHolder>>) {
        this.viewModels.clear()
        this.viewTypeMap.clear()
        addAll(viewModels)
        notifyDataSetChanged()
    }

    fun addViewModel(viewModel: BaseViewModel<RecyclerView.ViewHolder>) {
        this.viewModels.add(viewModel)
        viewTypeMap.put(viewModel.getViewType(), viewModel)
        val position = getPosition(viewModel)
        notifyItemInserted(position)
    }

    fun removeViewModel(position: Int) {
        if (position < -1 || position >= viewModels.size) {
            return
        }
        viewModels.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun removeViewModel(viewModel: BaseViewModel<RecyclerView.ViewHolder>) {
        val position = getPosition(viewModel)
        removeViewModel(position)
    }

    private fun getPosition(viewModel: BaseViewModel<RecyclerView.ViewHolder>): Int {
        return viewModels.indexOf(viewModel)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return viewTypeMap.get(viewType)!!.createViewHolder(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        viewModels[position].bindViewHolder(holder)
    }


    override fun getItemCount(): Int {
        return viewModels.size
    }


    override fun getItemViewType(position: Int): Int {
        return viewModels.get(position).getViewType()
    }


    private fun addAll(viewModels: Collection<BaseViewModel<RecyclerView.ViewHolder>>?) {
        if (viewModels == null) {
            return
        }

        for (baseViewModel in viewModels) {
            this.viewModels.add(baseViewModel)

            //If there are multiple items of the same type the index will just update
            viewTypeMap.put(baseViewModel.getViewType(), baseViewModel)
        }
    }

    fun isEmpty(): Boolean {
        return viewModels.isEmpty()
    }


}
