package com.dineshprabha.chefsaga.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dineshprabha.chefsaga.R
import com.dineshprabha.chefsaga.adapters.CategoryMealsAdapter
import com.dineshprabha.chefsaga.databinding.ActivityCategoryMealsBinding
import com.dineshprabha.chefsaga.ui.fragment.HomeFragment
import com.dineshprabha.chefsaga.viewmodel.CategoryMealViewModel
import com.dineshprabha.chefsaga.viewmodel.MealViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding : ActivityCategoryMealsBinding
    lateinit var categoryMealViewModel: CategoryMealViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    companion object{
        const val CATEGORY_NAME = "chefsaga.categoryName"
        const val CATEGORY_ID = "chefsaga.categoryId"
        const val CATEGORY_THUMB = "chefsaga.categoryThumb"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealViewModel = ViewModelProvider(this).get(CategoryMealViewModel::class.java)
        categoryMealsAdapter = CategoryMealsAdapter()
        prepareRecyclerView()
        onCategoriesItemClick()

        categoryMealViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealViewModel.observeMealLiveData().observe(this, Observer { mealsList ->
            mealsList.forEach {
                categoryMealsAdapter.setMealsList(mealsList)
            }
        })

    }

    private fun onCategoriesItemClick() {

        categoryMealsAdapter.onItemClick = { meal ->
            val intent = Intent(applicationContext, FoodActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        binding.rvCategoryMeals.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}