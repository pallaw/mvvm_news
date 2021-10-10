package com.pallaw.mvvmnews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pallaw.mvvmnews.data.db.ArticleDatabase
import com.pallaw.mvvmnews.data.repository.NewsRepository
import com.pallaw.mvvmnews.databinding.ActivityMainBinding
import com.pallaw.mvvmnews.viewmodel.NewsViewModel
import com.pallaw.mvvmnews.viewmodel.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //this should be first thing because other fragments are dependent on this.
        setupViewModel()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()

    }

    private fun setupViewModel() {
        val newsRepo = NewsRepository(ArticleDatabase(this))
        val newsViewModelProviderFactory = NewsViewModelProviderFactory(newsRepo)
        viewModel = ViewModelProvider(this, newsViewModelProviderFactory)
            .get(NewsViewModel::class.java)
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_breaking,
                R.id.navigation_saved,
                R.id.navigation_search
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
                || super.onSupportNavigateUp()
    }
}