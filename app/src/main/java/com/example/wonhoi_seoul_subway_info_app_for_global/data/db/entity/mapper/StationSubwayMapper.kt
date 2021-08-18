package com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.mapper

import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.*
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Station
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.StationIdAndEngNameModel
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Subway

fun StationWithSubwayEntity.toStation() = Station(
    name = station.stationName,
    engName = station.stationEngName,
    id = station.stationId,
    isFavorited = station.isFavorited,
    connectedSubway = subway.toSubways(),    // { Subway.findById(it.subwayId) } 를 사용해서 아이디 뿐만아니라 subway 의 다른 정보들도 함께 넣어줌.
)

fun Station.toStationEntitiy() =
    StationEntity(
        stationName = name,
        stationEngName = engName,
        stationId = id,
        isFavorited = isFavorited
    )

fun StationIdAndEngNameEntity.toStationIdAndEngNameModel() =
    StationIdAndEngNameModel(
        id = stationId,
        engName = stationEngName
    )



fun List<StationWithSubwayEntity>.toStations() = map {
    it.toStation()
}

fun List<SubwayEntity>.toSubways(): List<Subway> = map { Subway.findById(it.subwayId) }

fun List<StationIdAndEngNameEntity>.toStationIdAndEngNameModel(): List<StationIdAndEngNameModel> = map {
    it.toStationIdAndEngNameModel()
}


