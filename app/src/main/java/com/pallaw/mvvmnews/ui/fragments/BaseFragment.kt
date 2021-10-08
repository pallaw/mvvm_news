package com.pallaw.mvvmnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pallaw.mvvmnews.MainActivity
import com.pallaw.mvvmnews.viewmodel.NewsViewModel

open class BaseFragment: Fragment() {

    lateinit var newsViewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as MainActivity).viewModel
    }
}