package com.fox.alyxnews.repository

import com.fox.alyxnews.api.NewsApi
import com.fox.alyxnews.data.responces.Article
import kotlinx.coroutines.CompletableJob

class NewsRepository(private val apiInterface: NewsApi) : BaseRepository() {

    private val TAG = NewsRepository::class.java.simpleName
    private var job: CompletableJob? = null

    suspend fun getLatestNews(q: String, language: String): MutableList<Article>? {
        return safeApiCall(
            // await the result of deffered type
            call = { apiInterface.fetchNewsAsync(q, language) }, //"bitcoin" "publishedAt"
            error = "Error: fetching news"
        )?.articles?.toMutableList()
    }
}