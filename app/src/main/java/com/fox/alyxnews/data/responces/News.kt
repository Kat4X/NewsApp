package com.fox.alyxnews.data.responces



data class News(
    var status: String,
    var totalResults: Int,
    var articles: List<Article>
)

data class Article(
    var source: Source?,
    var author: String,
    var title: String,
    var description: String,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String,
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
    var id: Any?,
    var name: String?
)