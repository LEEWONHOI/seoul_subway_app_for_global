package com.example.wonhoi_seoul_subway_info_app_for_global.data.api

import com.example.wonhoi_seoul_subway_info_app_for_global.BuildConfig
import com.example.wonhoi_seoul_subway_info_app_for_global.data.api.response.RealtimeStationArrivals
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StationArrivalsApi {

   @GET("api/subway/${BuildConfig.SEOUL_API_ACCESS_KEY}/json/realtimeStationArrival/0/100/{stationName}")
    suspend fun getRealtimeStationArrivals(@Path("stationName") stationName: String) : Response<RealtimeStationArrivals>
}