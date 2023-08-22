package com.example.chapter15sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.chapter15sunnyweather.logic.Repository
import com.example.chapter15sunnyweather.logic.model.Place

/**
 * @author: wayne
 * @date: 2023/8/18
 * @description：ViewModel,UI和逻辑层之间的桥梁
 */
class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun savePlace(place: Place)=Repository.savePlace(place)
    fun getSavedPlace()=Repository.getSavedPlace()
    fun isPlaceSaved()=Repository.isPlaceSaved()

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}