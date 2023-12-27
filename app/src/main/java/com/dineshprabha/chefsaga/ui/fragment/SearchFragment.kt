package com.dineshprabha.chefsaga.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dineshprabha.chefsaga.MainActivity
import com.dineshprabha.chefsaga.R
import com.dineshprabha.chefsaga.adapters.FavoritesAdapter
import com.dineshprabha.chefsaga.databinding.FragmentSearchBinding
import com.dineshprabha.chefsaga.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var searchAdapter : FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.imgSearchArrow.setOnClickListener { searchMeals() }

        onserveSearchMealsLiveData()

        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener {searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                homeViewModel.searchMeal(searchQuery.toString())
            }
        }
    }

    private fun onserveSearchMealsLiveData() {

        homeViewModel.observeSearchMealLiveData().observe(viewLifecycleOwner, Observer { mealsList ->
            searchAdapter.differ.submitList(mealsList)
        })
    }

    private fun searchMeals() {

        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            homeViewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecyclerView() {

        searchAdapter = FavoritesAdapter()
        binding.rvSearchMeals.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }
}