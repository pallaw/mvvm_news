package com.pallaw.mvvmnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pallaw.mvvmnews.data.Resource
import com.pallaw.mvvmnews.databinding.FragmentBreakingBinding
import com.pallaw.mvvmnews.ui.adapters.NewsAdapter
import com.pallaw.mvvmnews.util.toggleVisibility

class BreakingNewsFragment : BaseFragment() {

    private lateinit var newsAdapter: NewsAdapter
    private var _binding: FragmentBreakingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreakingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        newsViewModel.breakingNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBar.toggleVisibility(false)
                    response.Data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    binding.progressBar.toggleVisibility(true)
                }
            }
        })
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsAdapter.setOnItemClickListener {
            findNavController().navigate(
                BreakingNewsFragmentDirections.actionNavigationBreakingToArticleDetails(it)
            )
        }

    }
}