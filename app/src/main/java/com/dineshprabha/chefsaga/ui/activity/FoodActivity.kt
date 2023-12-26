package com.dineshprabha.chefsaga.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dineshprabha.chefsaga.R
import com.dineshprabha.chefsaga.data.Meal
import com.dineshprabha.chefsaga.databinding.ActivityFoodBinding
import com.dineshprabha.chefsaga.ui.fragment.HomeFragment
import com.dineshprabha.chefsaga.viewmodel.HomeViewModel
import com.dineshprabha.chefsaga.viewmodel.MealViewModel

class FoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var youTubeLink : String
    private lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealViewModel = ViewModelProvider(this).get(MealViewModel::class.java)

        getMealInfromationFromIntent()
        setInformationInViews()
        mealViewModel.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYouTube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealViewModel.observeMealDetailsLiveData().observe(this, object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                val meal = value
                binding.tvCategory.text = "Categroy: ${meal!!.strCategory}"
                binding.tvArea.text = "Area: ${meal.strArea}"
                binding.tvDescription.text = meal.strInstructions
                youTubeLink = meal.strYoutube
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealThumb)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfromationFromIntent() {

        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}