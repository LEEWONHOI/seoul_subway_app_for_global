package com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity

import androidx.room.Entity


//Define many-to-many relationships
@Entity(primaryKeys = ["stationName", "subwayId"])
data class StationSubwayCrossRefEntity(
    val stationName : String,
    val subwayId : Int,
)