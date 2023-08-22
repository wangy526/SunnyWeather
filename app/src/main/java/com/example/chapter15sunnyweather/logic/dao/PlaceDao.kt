package com.example.chapter15sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.chapter15sunnyweather.SunnyWeatherApplication
import com.example.chapter15sunnyweather.logic.model.Place
import com.google.gson.Gson

/**
 * @author: wayne
 * @date: 2023/8/22
 * @description：记录选中城市的存储
 */
object PlaceDao {

    //将Place对象存储到SharePreference中
    fun savePlace(place: Place){
        sharePreference().edit {
            putString("place",Gson().toJson(place))
        }
    }

    //从SharePreference中读取Place对象
    fun getSavedPlace():Place{
        val placeJson= sharePreference().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    //判断是否有数据已被存储
    fun isPlaceSaved()= sharePreference().contains("place")

    private fun sharePreference()=SunnyWeatherApplication.mContext.
    getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)
}