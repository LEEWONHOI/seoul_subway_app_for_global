package com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stations

import androidx.annotation.StringRes
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Station

sealed class StationsState {

    object Uninitialized: StationsState()

    object Loading: StationsState()

    data class Success(
        val stations : List<Station>
    ): StationsState()

    data class Error(
        @StringRes val messageId: Int
    ): StationsState()

}