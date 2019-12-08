package com.yue.dailynews.retrofit.response

import androidx.annotation.NonNull
import java.io.Serializable

data class LikeOrDisLikeUser(
    @NonNull
    var title:String,
    var isLike:Boolean
):Serializable{
    constructor():this("",false)
}
