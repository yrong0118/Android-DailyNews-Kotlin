package com.yue.dailynews.retrofit

import com.yue.dailynews.retrofit.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import io.reactivex.Observable;


interface NewsRequestApi {
    @GET("top-headlines")
    fun getNewsByCountry(@Query("country") country: String,@Query("apiKey") apiKey: String): Observable<BaseResponse>

}


