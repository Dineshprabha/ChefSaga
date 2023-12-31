package com.dineshprabha.chefsaga.retrofit

import com.dineshprabha.chefsaga.data.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}