package com.yue.dailynews.retrofit.response

import com.google.gson.annotations.SerializedName

data class BaseResponse (
    val status: String,
    val totalResult: Int,
    @SerializedName("articles")
    val articles: List<News>
//    companion object{
//
//    }
)
