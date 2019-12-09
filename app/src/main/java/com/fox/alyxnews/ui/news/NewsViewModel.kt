package com.fox.alyxnews.ui.news

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fox.alyxnews.api.NewsService
import com.fox.alyxnews.models.Article
import com.fox.alyxnews.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    //    private val parentJob = Job()
//    private val coroutineContext : CoroutineContext get() = parentJob + IO
//    private val scope = CoroutineScope(coroutineContext)
    var repository: NewsRepository = NewsRepository(NewsService.apiService)

    val newsLiveData = MutableLiveData<MutableList<Article>>()
    val isRefreshing = MutableLiveData<Boolean>(false)

    @WorkerThread
    fun getNews(q: String, language: String) {
        viewModelScope.launch {
            val latestNews = repository.getLatestNews(q, language)
            newsLiveData.postValue(latestNews)
        }
//        scope.launch {
//            val latestNews = repository.getLatestNews(q, language)
//            newsLiveData.postValue(latestNews)
//        }
    }

//    fun cancelJobs() = coroutineContext.cancel()
}