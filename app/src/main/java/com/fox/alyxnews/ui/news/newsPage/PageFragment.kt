package com.fox.alyxnews.ui.news.newsPage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.fox.alyxnews.App

import com.fox.alyxnews.R
import kotlinx.android.synthetic.main.fragment_page.*


class PageFragment : Fragment() {

    private lateinit var viewModel: PageViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        navController = Navigation.findNavController(view)
        initUi()
    }

    private fun initViewModel() {
        viewModel = activity?.let {
            ViewModelProvider(it).get(PageViewModel::class.java)
        }!!

    }

    private fun initUi() {
        news_web_view.loadUrl(App.getUrl())
    }

}
