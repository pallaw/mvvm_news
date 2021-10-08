package com.pallaw.mvvmnews.viewmodel

import androidx.lifecycle.ViewModel
import com.pallaw.mvvmnews.data.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel()