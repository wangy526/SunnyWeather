package com.example.chapter15sunnyweather.logic.model

/**
 * @author: wayne
 * @date: 2023/8/21
 * @description：
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)