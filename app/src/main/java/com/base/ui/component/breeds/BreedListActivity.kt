package com.base.ui.component.breeds

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.R
import com.base.data.dto.breed.BreedItem
import com.base.databinding.HomeActivityBinding
import com.base.ui.base.BaseActivity
import com.base.ui.base.listeners.RecyclerItemListener
import com.base.ui.component.breeds.adapter.BreedsAdapter
import com.base.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedListActivity : BaseActivity(), RecyclerItemListener {
    private lateinit var binding: HomeActivityBinding
    private val recipesListViewModel: BreedListViewModel by viewModels()
    private lateinit var breedAdapter:BreedsAdapter
    private var searchJob: Job? = null

    override fun initViewBinding() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        breedAdapter = BreedsAdapter()
        supportActionBar?.title = getString(R.string.breed)
        val layoutManager = LinearLayoutManager(this)
        binding.rvRecipesList.layoutManager = layoutManager
        binding.rvRecipesList.setHasFixedSize(true)

        binding.rvRecipesList.adapter = breedAdapter
        breedAdapter.setOnclickListener(this)
        breedAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                showLoadingView()
            } else {
                showDataView(true)
            }
        }
        lifecycleScope.launch {
            recipesListViewModel.getBreeds()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_by_name)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // type id like awir, abys
                if (newText?.length == 4) {
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        breedAdapter.submitData(lifecycle, PagingData.empty())
                        recipesListViewModel.searchBreeds(newText)
                    }
                } else if (newText.isNullOrEmpty()) {
                    breedAdapter.submitData(lifecycle, PagingData.empty())
                    lifecycleScope.launch {
                        recipesListViewModel.getBreeds()
                    }
                }
                return true
            }
        }
        )
        return true
    }

    private fun bindListData(recipes: PagingData<BreedItem>) {
        if (recipes != null) {
            breedAdapter.submitData(lifecycle, recipes)
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvRecipesList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvRecipesList.toGone()
    }

    override fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            recipesListViewModel.breedsLiveData.observe(this@BreedListActivity) { result ->
                bindListData(recipes = result)
            }
        }

        lifecycleScope.launchWhenStarted {
            recipesListViewModel.breedsSearchLiveData.observe(this@BreedListActivity) { result ->
                bindListData(recipes = result)
            }
        }
    }

    override suspend fun onItemSelected(recipe: BreedItem) {
        recipesListViewModel.downloadBreed(recipe)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob?.cancel()
    }
}
