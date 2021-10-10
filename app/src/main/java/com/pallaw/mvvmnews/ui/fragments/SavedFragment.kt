package com.pallaw.mvvmnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pallaw.mvvmnews.data.Resource
import com.pallaw.mvvmnews.databinding.FragmentSavedBinding
import com.pallaw.mvvmnews.ui.adapters.NewsAdapter
import com.pallaw.mvvmnews.util.toggleVisibility

class SavedFragment : BaseFragment() {


    private var newsAdapter: NewsAdapter = NewsAdapter()
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
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

        newsViewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.differ.submitList(articles)
        })


    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsAdapter.setOnItemClickListener {
            findNavController().navigate(
                SavedFragmentDirections.actionNavigationSavedToArticleDetails(it)
            )
        }


        //drag functionality for recyclerview
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val articleToBeDeleted = newsAdapter.differ.currentList[position]
                newsViewModel.deleteArticle(articleToBeDeleted)

                Snackbar.make(binding.root, "Successfully delted article", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            newsViewModel.saveArticle(articleToBeDeleted)
                        }
                        show()
                    }
            }

        }).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}