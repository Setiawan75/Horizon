package com.world.horizon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    var author: String? = "",
    var title: String = "",
    var description: String = "",
    var url: String = "",
    var urlToImage: String = "",
    var publishedAt: String = "",
    /*Local*/
    var name: String = "",
//    var content: String = "",
) : Parcelable

data class Articles(var articles: ArrayList<Article>)
