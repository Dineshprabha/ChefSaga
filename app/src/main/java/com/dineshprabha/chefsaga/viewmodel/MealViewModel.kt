package com.dineshprabha.chefsaga.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dineshprabha.chefsaga.data.Meal
import com.dineshprabha.chefsaga.data.MealList
import com.dineshprabha.chefsaga.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel : ViewModel() {

    private val mealDetailLiveDta = MutableLiveData<Meal>()

    fun getMealDetail(id : String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    mealDetailLiveDta.value = response.body()!!.meals[0]
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("FoodActivity", t.message.toString())
            }

        })
    }

    fun observeMealDetailsLiveData():LiveData<Meal>{
        return mealDetailLiveDta
    }
}