package com.yue.dailynews.retrofit.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import androidx.annotation.NonNull

data class News(
    var author : String,
    @NonNull
    var title : String,
    var description :String,
    var url : String,
    @SerializedName("urlToImage") //add serializedAnnotation
    var image : String,
    @SerializedName("publishedAt") //add serializedAnnotation
    var time : String
    ):Serializable{
        constructor():this("","","","","","")
    }

//class News(var author : String,
//           var title : String,
//           var description :String,
//           var url : String,
//           @SerializedName("urlToImage") //add serializedAnnotation
//           var image : String,
//           @SerializedName("publishedAt") //add serializedAnnotation
//           var time : String): Serializable{
//}
//
//class News{
//    var author : String
//    get(){
//       return author
//    }
//    @NonNull
//    var title : String
//    var description :String
//    var url : String
//    @SerializedName("urlToImage") //add serializedAnnotation
//    var image : String
//    @SerializedName("publishedAt") //add serializedAnnotation
//    var time : String
//
//    constructor(author : String,title: String, description :String,url : String, image : String, time : String) {
//        this.author = author
//        this.title = title
//        this.description = description
//        this.url = url
//        this.image = image
//        this.time = time
//    }
//
//    constructor(){
//        this.author = ""
//        this.title = ""
//        this.description = ""
//        this.url = ""
//        this.image = ""
//        this.time = ""
//    }
//}



//data class BaseResponse (
//    val status: String,
//    val totalResult: Int,
//    @SerializedName("articles")
//    val articles: List<News>
////    companion object{
////
////    }
//)
