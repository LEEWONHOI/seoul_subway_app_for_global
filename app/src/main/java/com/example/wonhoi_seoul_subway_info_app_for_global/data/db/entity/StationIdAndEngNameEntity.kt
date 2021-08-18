package com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationIdAndEngNameEntity(
    @PrimaryKey  val stationId : String,
    val stationEngName : String,
)