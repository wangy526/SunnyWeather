package com.example.chapter15sunnyweather

import android.content.Context
import android.widget.Toast
import java.time.Duration

/**
 * @author: wayne
 * @date: 2023/8/18
 * @description：简化Toast
 */

fun String.showToast(duration:Int=Toast.LENGTH_LONG){
    Toast.makeText(SunnyWeatherApplication.mContext,this,duration).show()
}
fun Int.showToast(duration:Int=Toast.LENGTH_LONG){
    Toast.makeText(SunnyWeatherApplication.mContext,this.toString(),duration).show()
}