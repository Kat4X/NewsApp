package com.fox.alyxnews.api

import com.fox.alyxnews.models.News
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    suspend fun fetchNewsAsync(
        @Query("q") q: String? = null,
//        @Query("from") from: String? = null,
        @Query("language") language: String? = null,
        @Query("sortBy") sortBy: String? = null
    ): Response<News>
}