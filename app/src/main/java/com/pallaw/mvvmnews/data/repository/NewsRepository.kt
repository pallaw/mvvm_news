package com.pallaw.mvvmnews.data.repository

import com.pallaw.mvvmnews.data.api.RetrofitInstance
import com.pallaw.mvvmnews.data.db.ArticleDatabase
import com.pallaw.mvvmnews.data.model.Article

class NewsRepository(
    val db: ArticleDatabase,
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.search(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavednews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}