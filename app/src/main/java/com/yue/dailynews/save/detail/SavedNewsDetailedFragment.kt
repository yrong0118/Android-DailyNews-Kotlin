package com.yue.dailynews.save.detail


import android.media.tv.TvContract.Programs.Genres.NEWS
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yue.dailynews.R
import com.yue.dailynews.common.BaseViewModel
import com.yue.dailynews.common.TinBasicFragment
import com.yue.dailynews.common.Util
import com.yue.dailynews.common.ViewModelAdapter
import com.yue.dailynews.retrofit.response.News
import kotlinx.android.synthetic.main.go_back.*

/**
 * A simple [Fragment] subclass.
 */
class SavedNewsDetailedFragment : TinBasicFragment() {

    private lateinit var viewModelAdapter: ViewModelAdapter
    companion object {
        @JvmStatic
        private val NEWS = "news"
        fun newInstance(news: News): SavedNewsDetailedFragment {
            var args = Bundle()
            args.putSerializable(NEWS, news)
            var fragment = SavedNewsDetailedFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_saved_news_detailed, container, false)
        var recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        viewModelAdapter = ViewModelAdapter()
        recyclerView.setAdapter(viewModelAdapter)

        return view
    }


    override fun onResume() {
        super.onResume()
        var news = getArguments()!!.getSerializable(NEWS) as News
        loadNews(news)

    }


    private fun loadNews(news: News) {
        var viewModels: MutableList<BaseViewModel<RecyclerView.ViewHolder>> = mutableListOf<BaseViewModel<RecyclerView.ViewHolder>>()
        if(!Util.isStringEmpty(news.title)){
            viewModels.add(TitleViewModel(news.title) as BaseViewModel<RecyclerView.ViewHolder>)
        }
//
        if (!Util.isStringEmpty(news.author) || !Util.isStringEmpty(news.time)) {
            viewModels.add(AuthorViewModel(news.author, news.time) as BaseViewModel<RecyclerView.ViewHolder>)
        }
        if (!Util.isStringEmpty(news.image)){
            viewModels.add(ImageViewModel(news.image) as BaseViewModel<RecyclerView.ViewHolder>)
        }
        if(!Util.isStringEmpty(news.description)){
            viewModels.add(DescriptionViewModel(news.description) as BaseViewModel<RecyclerView.ViewHolder>)
        }
        viewModels.add(goBackViewModel(tinFragmentManager,this) as BaseViewModel<RecyclerView.ViewHolder>)


        viewModelAdapter.addViewModels(viewModels)
    }

}
