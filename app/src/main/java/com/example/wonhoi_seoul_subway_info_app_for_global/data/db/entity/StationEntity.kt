package com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationEntity(
    @PrimaryKey val stationName : String,
    val stationEngName : String,
    val stationId : String,
    val isFavorited :Boolean = false
    )