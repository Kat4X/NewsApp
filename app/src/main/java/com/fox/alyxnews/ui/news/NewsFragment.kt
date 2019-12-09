package com.fox.alyxnews.ui.news


import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fox.alyxnews.App
import com.fox.alyxnews.R
import com.fox.alyxnews.models.Article
import com.fox.alyxnews.ui.news.newsPage.PageFragment
import com.fox.alyxnews.util.TopSpacingDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.coroutines.*

class NewsFragment : Fragment(),
    NewsListAdapter.Interaction {

    lateinit var viewModel: NewsViewModel
    lateinit var viewModelFactory: NewsViewModelFactory
    lateinit var newsListAdapter: NewsListAdapter
    lateinit var navController: NavController

    companion object {
        private val TAG = NewsFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false) //fragment_news
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        initViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUi()
        initRecyclerView()
    }

    private fun initViewModel() {
        viewModelFactory = NewsViewModelFactory()
        viewModel =
            ViewModelProvider(this@NewsFragment, viewModelFactory).get(NewsViewModel::class.java)
        subscribeObserver()

    }

    private fun subscribeObserver() {
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { news ->
            Log.d(TAG, "$news")
            newsListAdapter.submitList(news)
            viewModel.isRefreshing.value = false
        })

        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            if (it == false){
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        swipe_r_l.isRefreshing = false
                    }
                }
            }
        })
    }

    private fun initUi() {
        initSwipeRefreshLayout()
    }

    private fun initSwipeRefreshLayout() {

        swipe_r_l.setOnRefreshListener {
            viewModel.getNews("technology", "ru")
            viewModel.isRefreshing.value = true
//            CoroutineScope(Dispatchers.IO).launch {
//                delay(1000)
//                withContext(Dispatchers.Main) {
//                    swipe_r_l.isRefreshing = false
//                }
//            }
        }
    }

    override fun onItemSelected(position: Int, item: Article) {
        Log.d(TAG, "$position")
        Log.d(TAG, "$item")

        item.url?.let { App.setUrl(it) }
        navController.navigate(R.id.pageFragment)
    }

    private fun initRecyclerView() {
        news_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingDecoration = TopSpacingDecoration(30)
            addItemDecoration(topSpacingDecoration)
            newsListAdapter = NewsListAdapter(this@NewsFragment)
            adapter = newsListAdapter
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        viewModel.cancelJobs()
//    }

}
