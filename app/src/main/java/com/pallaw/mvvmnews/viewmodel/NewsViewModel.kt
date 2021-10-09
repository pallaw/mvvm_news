package com.pallaw.mvvmnews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pallaw.mvvmnews.data.Resource
import com.pallaw.mvvmnews.data.model.NewsResponse
import com.pallaw.mvvmnews.data.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1

    val searchedNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchedNewsPage = 1

    init {
        getBreakingNews("IN")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchNews(query: String) = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        val searchResponse = newsRepository.searchNews(query, searchedNewsPage)
        searchedNews.postValue(handleSearchResponse(searchResponse))
    }

    private fun handleSearchResponse(searchResponse: Response<NewsResponse>): Resource<NewsResponse> {
        if (searchResponse.isSuccessful) {
            searchResponse.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(searchResponse.message())
    }

}