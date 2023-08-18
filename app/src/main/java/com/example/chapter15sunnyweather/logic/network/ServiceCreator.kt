package com.example.chapter15sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: wayne
 * @date: 2023/8/18
 * @description：Retrofit构建器
 */
object ServiceCreator {

    private const val BASE_URL= "https://api.caiyunapp.com/"

    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass:Class<T>):T= retrofit.create(serviceClass)

    //泛型实例化的函数必须是内联函数
    inline fun <reified T> create():T= create(T::class.java)
}