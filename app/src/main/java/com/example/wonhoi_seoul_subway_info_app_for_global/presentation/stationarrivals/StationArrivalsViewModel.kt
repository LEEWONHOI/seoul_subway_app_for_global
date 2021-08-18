package com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stationarrivals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wonhoi_seoul_subway_info_app_for_global.R
import com.example.wonhoi_seoul_subway_info_app_for_global.data.repository.StationRepository
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Station
import com.example.wonhoi_seoul_subway_info_app_for_global.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StationArrivalsViewModel(
    private val station: Station,
    private val stationRepository: StationRepository,
) : BaseViewModel() {

    private var _stationArrivalsLiveData = MutableLiveData<StationArrivalsState>(StationArrivalsState.Uninitialized)
    val stationArrivalsLiveData: LiveData<StationArrivalsState> = _stationArrivalsLiveData

    private val stationNameAndEngNameHashMap = HashMap<String, String>()

    override fun fetchData(): Job = viewModelScope.launch {
        getStationIdAndName()
        fetchStationArrivals()
    }

    fun fetchStationArrivals() = viewModelScope.launch {
        try {
            _stationArrivalsLiveData.value = StationArrivalsState.Loading
            _stationArrivalsLiveData.value = StationArrivalsState.Success(
                stationRepository.getStationArrivals(station.name),
                stationNameAndEngNameHashMap
            )

        } catch (exception: Exception) {
            _stationArrivalsLiveData.value = StationArrivalsState.Error(
                R.string.unknown_problem
            )
            exception.printStackTrace()
        }
    }

    private fun getStationIdAndName() = viewModelScope.launch {
        stationRepository.getStationIdAndName().onEach { staionTest ->
            stationNameAndEngNameHashMap.put(staionTest.id, staionTest.engName)
        }
    }

    fun toggleStationFavorite() = viewModelScope.launch {
        stationRepository.updateStation(station.copy(isFavorited = !station.isFavorited))
    }

}