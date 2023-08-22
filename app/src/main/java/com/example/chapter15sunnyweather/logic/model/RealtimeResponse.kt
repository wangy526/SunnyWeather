package com.example.chapter15sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @author: wayne
 * @date: 2023/8/21
 * @description：实时天气的数模模型
 */

data class RealtimeResponse(val status: String, val result: Result) {

    //将所有的数据模型定义在内部，防止出现跟其他接口数据类型有同名冲突
    data class Result(val realtime: Realtime)

    data class Realtime(
        val temperature: Float, val skycon: String,
        @SerializedName("air_quality") val airQuality: AirQuality,
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)

}