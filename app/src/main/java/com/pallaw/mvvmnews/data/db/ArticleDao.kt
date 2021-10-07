package com.pallaw.mvvmnews.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pallaw.mvvmnews.data.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: ArticleDao): Long

    @Query("SELECT * FROM article")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}