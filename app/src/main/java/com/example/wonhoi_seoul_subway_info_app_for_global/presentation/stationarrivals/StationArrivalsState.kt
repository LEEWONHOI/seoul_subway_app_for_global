package com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stationarrivals

import androidx.annotation.StringRes
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.ArrivalInformation

sealed class StationArrivalsState {

    object Uninitialized: StationArrivalsState()

    object Loading: StationArrivalsState()

    data class Success(
        val arriveInfo : List<ArrivalInformation>,
        val stationNameAndEngNameHashMap : HashMap<String, String>
    ): StationArrivalsState()

    data class Error(
        @StringRes val messageId: Int
    ): StationArrivalsState()

}