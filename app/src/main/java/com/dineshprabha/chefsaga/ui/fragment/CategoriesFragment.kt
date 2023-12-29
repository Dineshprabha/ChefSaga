package com.dineshprabha.chefsaga.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.dineshprabha.chefsaga.MainActivity
import com.dineshprabha.chefsaga.R
import com.dineshprabha.chefsaga.adapters.CategoriesAdapter
import com.dineshprabha.chefsaga.adapters.MostPopularAdapter
import com.dineshprabha.chefsaga.databinding.FragmentCategoriesBinding
import com.dineshprabha.chefsaga.ui.activity.CategoryMealsActivity
import com.dineshprabha.chefsaga.viewmodel.HomeViewModel


class CategoriesFragment : Fragment() {
    private lateinit var binding : FragmentCategoriesBinding
    private var categoriesAdapter = CategoriesAdapter()
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observerCategoryLiveData()
        onCategoriesItemClick()

    }

    private fun onCategoriesItemClick() {
        categoriesAdapter.onItemClick = {category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoryLiveData() {
        homeViewModel.observeCategroiesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categories.forEach { category ->
                categoriesAdapter.setCategoryList(categories)
            }
        })
    }

}