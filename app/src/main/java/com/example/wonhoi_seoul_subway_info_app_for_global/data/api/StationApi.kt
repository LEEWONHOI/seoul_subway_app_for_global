package com.example.wonhoi_seoul_subway_info_app_for_global.data.api

import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.StationEntity
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.StationIdAndEngNameEntity
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.SubwayEntity

interface StationApi {

    suspend fun getStationDataUpdatedTimeMillis() : Long

    suspend fun getStationSubways() : List<Pair<StationEntity, SubwayEntity>>

    suspend fun getStationIdAndEngName(): List<StationIdAndEngNameEntity>

}