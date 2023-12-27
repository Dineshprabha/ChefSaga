package com.dineshprabha.chefsaga.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dineshprabha.chefsaga.MainActivity
import com.dineshprabha.chefsaga.R
import com.dineshprabha.chefsaga.adapters.CategoriesAdapter
import com.dineshprabha.chefsaga.adapters.MostPopularAdapter
import com.dineshprabha.chefsaga.data.Category
import com.dineshprabha.chefsaga.data.MealsByCategory
import com.dineshprabha.chefsaga.data.Meal
import com.dineshprabha.chefsaga.databinding.FragmentHomeBinding
import com.dineshprabha.chefsaga.ui.activity.CategoryMealsActivity
import com.dineshprabha.chefsaga.ui.activity.FoodActivity
import com.dineshprabha.chefsaga.ui.fragment.bottomsheet.MealBottomSheetFragment
import com.dineshprabha.chefsaga.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private var popularItemAdapter = MostPopularAdapter()
    private var categoriesAdapter = CategoriesAdapter()

    companion object{
        const val MEAL_ID = "chefsaga.idMeal"
        const val MEAL_NAME = "chefsaga.nameMeal"
        const val MEAL_THUMB = "chefsaga.thumbMeal"
        const val CATEGORY_NAME = "chefsaga.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel = (activity as MainActivity).viewModel
        popularItemAdapter = MostPopularAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPopularItemsRV()
        homeViewModel.getRandomMeal()
        setupObserver()
        onRandomMeaClick()
        homeViewModel.getPopularItems()
        homeViewModel.getCategories()
        observePopularItemsLiveData()
        onPupularItemClick()
        observerCategoryLiveData()
        setUpCategoriesRv()
        onCategoriesItemClick()

        onPopularItemLongClick()
        onSearchIconClick()

    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {

        popularItemAdapter.onLongItemClick = {meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun onCategoriesItemClick() {
        categoriesAdapter.onItemClick = {category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun setUpCategoriesRv() {
        binding.rvCategoryItems.apply {
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

    private fun onPupularItemClick() {
        popularItemAdapter.onItemClick = { meal ->
            val intent = Intent(activity, FoodActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setupPopularItemsRV() {
        binding.rvPopularItems.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeViewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) {mealList ->
        popularItemAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)

        }
    }

    private fun onRandomMeaClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, FoodActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setupObserver() {
        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment).load(meal.strMealThumb).into(binding.imageRandomMeal)
            this.randomMeal = meal
        }
    }
}