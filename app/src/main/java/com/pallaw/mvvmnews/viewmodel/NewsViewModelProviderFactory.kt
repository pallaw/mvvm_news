package com.pallaw.mvvmnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pallaw.mvvmnews.data.repository.NewsRepository

class NewsViewModelProviderFactory(val newsRespository: NewsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRespository) as T
    }
}