package com.yue.dailynews.retrofit

import android.util.Base64
import com.yue.dailynews.R
import com.yue.dailynews.retrofit.response.News
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.net.URLEncoder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class RetrofitClient {
    companion object {
        val BASE_URL = "https://newsapi.org/v2/"

        val instance: Retrofit
        init {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient)
                .build()
        }


        val getHttpClient: OkHttpClient
            get() {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                var builder: OkHttpClient.Builder = OkHttpClient.Builder()
                    .addInterceptor(NewsInterceptor())
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                return builder.build()
            }


        private class NewsInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response { //To change body of created functions use File | Settings | File Templates.
                var original: Request = chain.request()
                var request: Request = original
                    .newBuilder()
                    .build()
                return chain.proceed(request)
            }


        }
    }
}
