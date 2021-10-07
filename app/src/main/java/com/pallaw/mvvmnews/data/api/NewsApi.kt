package com.pallaw.mvvmnews.data.api

import com.pallaw.mvvmnews.data.model.NewsResponse
import com.pallaw.mvvmnews.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "IN",
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY,
        ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun search(
        @Query("q") searchQuery: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY,
        ): Response<NewsResponse>

}