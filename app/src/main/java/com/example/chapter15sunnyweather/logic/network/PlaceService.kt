package com.example.chapter15sunnyweather.logic.network

import com.example.chapter15sunnyweather.SunnyWeatherApplication
import com.example.chapter15sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author: wayne
 * @date: 2023/8/18
 * @description：用于访问彩云天气城市搜索API的接口
 */
interface PlaceService {

    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}