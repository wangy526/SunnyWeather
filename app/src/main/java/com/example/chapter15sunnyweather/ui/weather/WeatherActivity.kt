package com.example.chapter15sunnyweather.ui.weather

import android.content.Context
import android.graphics.Color
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chapter15sunnyweather.R
import com.example.chapter15sunnyweather.databinding.ActivityWeatherBinding
import com.example.chapter15sunnyweather.logic.model.Weather
import com.example.chapter15sunnyweather.logic.model.getSky
import com.example.chapter15sunnyweather.showToast
import com.example.chapter15sunnyweather.ui.place.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.log

class WeatherActivity : AppCompatActivity() {

    lateinit var mBinding:ActivityWeatherBinding
     val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityWeatherBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        if (viewModel.locationLng.isEmpty()){
            viewModel.locationLng=intent.getStringExtra("location_lng")?:""
        }
        if (viewModel.locationLat.isEmpty()){
            viewModel.locationLat=intent.getStringExtra("location_lat")?:""
        }
        if (viewModel.placeName.isEmpty()){
            viewModel.placeName=intent.getStringExtra("place_name")?:""
        }

        viewModel.weatherLiveData.observe(this, Observer { result->
            val weather=result.getOrNull()
            if (weather!=null){
                showWeatherInfo(weather)
            }else{
                "无法获取天气信息".showToast(Toast.LENGTH_SHORT)
                result.exceptionOrNull()?.printStackTrace()
            }
            mBinding.swipeRefresh.isRefreshing=false
        })
        mBinding.swipeRefresh.setColorSchemeResources(com.google.android.material.R.color.design_default_color_primary)
        refreshWeather()
        mBinding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
        //将任务栏设置和背景图融合到一起，切将颜色改为透明
        // 设置页面全屏显示
        val decorView=window.decorView
        decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor=Color.TRANSPARENT
        //延伸显示区域到刘海
        val attr=window.attributes
        attr.layoutInDisplayCutoutMode=WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.attributes=attr

        //切换城市
        mBinding.nowLayoutPlaceName.navButton.setOnClickListener {
            mBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
        mBinding.drawerLayout.addDrawerListener(object :DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerClosed(drawerView: View) {
                val manager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
    }

    fun refreshWeather(){//刷新天气方法
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationLat)
        mBinding.swipeRefresh.isRefreshing=true
    }

    private fun showWeatherInfo(weather: Weather) {
        mBinding.nowLayoutPlaceName.placeName.text=viewModel.placeName
        val realtime=weather.realtime
        val daily=weather.daily
        //填充now.xml布局中的数据
        val currentTempText="${realtime.temperature.toInt()}°C"
        mBinding.nowLayoutPlaceName.currentTemp.text=currentTempText
        mBinding.nowLayoutPlaceName.currentSky.text= getSky(realtime.skycon).info
        val currentPM25Text="空气指数${realtime.airQuality.aqi.chn.toInt()}"
        mBinding.nowLayoutPlaceName.currentAQI.text=currentPM25Text
        mBinding.nowLayoutPlaceName.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        //填充forecast布局中的数据
        mBinding.forecastLayoutPlaceName.forecastLayout.removeAllViews()
        val days=daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(
                R.layout.forecast_item,
                mBinding.forecastLayoutPlaceName.forecastLayout,
                false
            )
            val dateInfo = view.findViewById<TextView>(R.id.dataInfo)
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
            val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)
            val simpleDataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDataFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.max.toInt()} ~ ${temperature.min.toInt()}°C"
            temperatureInfo.text = tempText
            mBinding.forecastLayoutPlaceName.forecastLayout.addView(view)
        }
        //填充life_index.xml布局中的数据
        val lifeIndex=daily.lifeIndex
        mBinding.lifeIndexLayoutPlaceName.coldRiskText.text=lifeIndex.coldRisk[0].desc
        mBinding.lifeIndexLayoutPlaceName.dressingText.text=lifeIndex.dressing[0].desc
        mBinding.lifeIndexLayoutPlaceName.ultravioletText.text=lifeIndex.ultraviolet[0].desc
        mBinding.lifeIndexLayoutPlaceName.carWashingText.text=lifeIndex.carWashing[0].desc
        mBinding.weatherLayout.visibility=View.VISIBLE
    }
}