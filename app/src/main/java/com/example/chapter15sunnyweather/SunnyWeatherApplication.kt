package com.example.chapter15sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication :Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext:Context
        const val TOKEN="CvNyqnOsW4Yy9Zzq"
    }

    override fun onCreate() {
        super.onCreate()
        mContext=applicationContext
    }
}