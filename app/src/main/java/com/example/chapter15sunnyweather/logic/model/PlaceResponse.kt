package com.example.chapter15sunnyweather.logic.model

import android.location.Address
import com.google.gson.annotations.SerializedName

/**
 * @author: wayne
 * @date: 2023/8/18
 * @description：数据模型
 */
data class PlaceResponse(val status:String,val places:List<Place>)

data class Place(val name:String,val location:Location,
                 @SerializedName("formatted_address") val address: String)

data class Location(val lng:String,val lat:String)