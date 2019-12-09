package com.fox.alyxnews.models


import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: Int,
    @SerializedName("articles")
    var articles: List<Article>
)

data class Article(
    @SerializedName("source")
    var source: Source?,
    @SerializedName("author")
    var author: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("url")
    var url: String?,
    @SerializedName("urlToImage")
    var urlToImage: String?,
    @SerializedName("publishedAt")
    var publishedAt: String,
    @SerializedName("content")
    var content: String?
) {
    override fun toString(): String {
        return "News: title=$title, \n" +
                "description=$description, \n" +
                "authorAndPublishedAt=$author $publishedAt" +
                "urlToImage=$urlToImage, \n"
    }
}

data class Source(
    @SerializedName("id")
    var id: Any?,
    @SerializedName("name")
    var name: String?
)