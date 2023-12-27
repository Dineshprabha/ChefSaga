package com.dineshprabha.chefsaga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.dineshprabha.chefsaga.database.MealDatabase
import com.dineshprabha.chefsaga.databinding.ActivityMainBinding
import com.dineshprabha.chefsaga.viewmodel.HomeViewModel
import com.dineshprabha.chefsaga.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    val viewModel : HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.main_fragment)

        NavigationUI.setupWithNavController(binding.btmNav, navController)
    }
}