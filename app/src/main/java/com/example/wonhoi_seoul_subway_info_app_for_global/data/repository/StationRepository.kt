package com.example.wonhoi_seoul_subway_info_app_for_global.data.repository

import com.example.wonhoi_seoul_subway_info_app_for_global.domain.ArrivalInformation
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Station
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.StationIdAndEngNameModel
import kotlinx.coroutines.flow.Flow

interface StationRepository {

    val stations : Flow<List<Station>>

    suspend fun refreshStations()

    suspend fun getStationArrivals(stationName : String) : List<ArrivalInformation>

    suspend fun updateStation(station: Station)

    suspend fun getStationIdAndName() : List<StationIdAndEngNameModel>

}