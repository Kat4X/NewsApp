package com.fox.alyxnews.ui.news

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fox.alyxnews.data.responces.Article
import com.fox.alyxnews.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    var repository: NewsRepository = NewsRepository(NewsService.apiService)

    val newsLiveData = MutableLiveData<MutableList<Article>>()
    val isRefreshing = MutableLiveData<Boolean>(false)

    @WorkerThread
    fun getNews(q: String, language: String) {
        viewModelScope.launch {
            val latestNews = repository.getLatestNews(q, language)
            newsLiveData.postValue(latestNews)
        }
    }
}