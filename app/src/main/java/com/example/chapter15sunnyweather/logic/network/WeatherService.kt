package com.example.chapter15sunnyweather.logic.network

import com.example.chapter15sunnyweather.SunnyWeatherApplication
import com.example.chapter15sunnyweather.logic.model.DailyResponse
import com.example.chapter15sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author: wayne
 * @date: 2023/8/21
 * @description：用于访问天气API的Retrofit接口
 */
interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String,
    ): Call<RealtimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>


}