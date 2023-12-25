package com.dineshprabha.chefsaga.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dineshprabha.chefsaga.R
import com.dineshprabha.chefsaga.data.Meal
import com.dineshprabha.chefsaga.data.MealList
import com.dineshprabha.chefsaga.databinding.FragmentHomeBinding
import com.dineshprabha.chefsaga.retrofit.RetrofitInstance
import com.dineshprabha.chefsaga.ui.activity.FoodActivity
import com.dineshprabha.chefsaga.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
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
        homeViewModel.getRandomMeal()
        setupObserver()
        onRandomMeaClick()

    }

    private fun onRandomMeaClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, FoodActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupObserver() {

        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment).load(value.strMealThumb).into(binding.imageRandomMeal)
            }

        })
    }
}