package com.example.chapter15sunnyweather.logic

import androidx.lifecycle.liveData
import com.example.chapter15sunnyweather.logic.model.Place
import com.example.chapter15sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Query
import java.lang.Exception
import java.lang.RuntimeException

/**
 * @author: wayne
 * @date: 2023/8/18
 * @description：仓库层的统一封装入口
 */
object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO){//运行在子线程中
        val result=try {
            val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status=="ok"){
                val places=placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}