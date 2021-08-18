package com.example.wonhoi_seoul_subway_info_app_for_global.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Station(
    val id : String,
    val name : String,
    val engName: String,
    val isFavorited : Boolean,
    val connectedSubway : List<Subway>
) : Parcelable
{

}