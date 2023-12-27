package com.dineshprabha.chefsaga.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dineshprabha.chefsaga.data.MealsByCategory
import com.dineshprabha.chefsaga.data.MealsByCategoryList
import com.dineshprabha.chefsaga.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel : ViewModel() {

    var categoryMealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName : String ){
        RetrofitInstance.api.getMealsByCategories(categoryName).enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let { mealsList ->
                    categoryMealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("CategoryMealsViewModel", t.message.toString())
            }

        })
    }

    fun observeMealLiveData():LiveData<List<MealsByCategory>>{
        return categoryMealsLiveData
    }
}