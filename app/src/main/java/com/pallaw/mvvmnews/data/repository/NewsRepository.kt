package com.pallaw.mvvmnews.data.repository

import com.pallaw.mvvmnews.data.api.NewsApi
import com.pallaw.mvvmnews.data.api.RetrofitInstance
import com.pallaw.mvvmnews.data.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase,
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}