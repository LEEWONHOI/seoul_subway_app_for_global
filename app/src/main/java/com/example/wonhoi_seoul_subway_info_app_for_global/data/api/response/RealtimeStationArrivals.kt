package com.example.wonhoi_seoul_subway_info_app_for_global.data.api.response

import com.google.gson.annotations.SerializedName

data class RealtimeStationArrivals(
    @SerializedName("errorMessage")
    val errorMessage: ErrorMessage? =null,
    @SerializedName("realtimeArrivalList")
    val realtimeArrivalList: List<RealtimeArrival>? = null
)