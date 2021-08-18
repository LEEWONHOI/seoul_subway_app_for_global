package com.example.wonhoi_seoul_subway_info_app_for_global.domain

data class ArrivalInformation(
    val subway: Subway,
    val direction: String,
    val thisStationMessage: String,
    val prevStationMessage: String,
    val destination: String,
    val updatedAt: String,
    val nextStationName: String,
    val thisStationName: String,
    val preStationName: String,
)