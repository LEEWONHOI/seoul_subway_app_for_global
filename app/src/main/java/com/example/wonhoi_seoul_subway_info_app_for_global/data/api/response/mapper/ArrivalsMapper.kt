package com.example.wonhoi_seoul_subway_info_app_for_global.data.api.response.mapper

import com.example.wonhoi_seoul_subway_info_app_for_global.data.api.response.RealtimeArrival
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.ArrivalInformation

import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Subway
import java.text.SimpleDateFormat
import java.util.*


private val apiDateFormat = SimpleDateFormat("yyy-MM-dd HH:mm:ss.'0'", Locale.KOREA)
private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA)


private const val INVALID_FIELD = "-"

fun RealtimeArrival.toArrivalInformation(): ArrivalInformation =

    ArrivalInformation(
        subway = Subway.findById(subwayId),
        direction = updnLine
            ?: INVALID_FIELD,
        destination = bstatnNm ?: INVALID_FIELD,
        thisStationMessage = arvlMsg2
            ?.replace(statnNm.toString(), "this station")
            ?.replace("[\\[\\]]".toRegex(), "")
            ?: INVALID_FIELD,
        prevStationMessage = arvlMsg2
            ?.replace(statnFid.toString(), "previous station")
            ?.replace("전역", "previous station")
            ?.replace("[\\[\\]]".toRegex(), "")
            ?: INVALID_FIELD,
        updatedAt = recptnDt
            ?.let {
                apiDateFormat.parse(it)
            }
            ?.let {
                dateFormat.format(it)
            }
            ?: INVALID_FIELD,

        nextStationName = statnTid
            ?: INVALID_FIELD,

        thisStationName = statnId
            ?: INVALID_FIELD,

        preStationName = statnFid
            ?: INVALID_FIELD
    )

fun List<RealtimeArrival>.toArrivalInformation(): List<ArrivalInformation> =
    map { realtimeArrival ->
        realtimeArrival.toArrivalInformation()
    }









